package com.thlee.work.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.thlee.work.model.HttpRequest;
import com.thlee.work.model.ServerSetting;
import com.thlee.work.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestProcessor implements Runnable {

    private ServerSetting serverSetting;
    private Socket request;

    public RequestProcessor(ServerSetting serverSetting, Socket request) {
        this.serverSetting = serverSetting;
        this.request = request;
    }

    @Override
    public void run() {

        HttpRequest httpRequest = null;
        PrintWriter out;
        BufferedReader in;

        try {
            out = new PrintWriter(request.getOutputStream(), true);
            in = new BufferedReader(
                new InputStreamReader(request.getInputStream()));

            // Http Method, URI, Host 정보를 획득한다
            httpRequest = HttpRequestParser.parseHttpRequest(request.getInputStream());
            log.info(httpRequest.toString());

            if (StringUtils.isEmpty(httpRequest.getUri())) {
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            ResponseHandler.handleErrorResponse(httpRequest, serverSetting, request, 500);
            return;
        }

        try {

            // Servlet Mapping 확인

            // 403, 404 여부 체크
            int errorCode = ValidationFilter.validate(httpRequest, serverSetting);

            if (errorCode != 200) {
                ResponseHandler.handleErrorResponse(httpRequest, serverSetting, request, errorCode);
                return;
            }

            // File return
            ResponseHandler.handleFileResponse(httpRequest, serverSetting, request);

        } catch (Exception e) {
            // 500 Error
            e.printStackTrace();
            ResponseHandler.handleErrorResponse(httpRequest, serverSetting, request, 500);
        } finally {
            try {
                in.close();
                out.close();
                request.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

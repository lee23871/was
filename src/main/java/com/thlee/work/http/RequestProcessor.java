package com.thlee.work.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import com.thlee.work.model.HttpRequest;
import com.thlee.work.model.HttpResponse;
import com.thlee.work.model.ServerSetting;
import com.thlee.work.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestProcessor implements Runnable {

    private ServerSetting serverSetting;
    private Socket socket;

    public RequestProcessor(ServerSetting serverSetting, Socket socket) {
        this.serverSetting = serverSetting;
        this.socket = socket;
    }

    @Override
    public void run() {

        HttpRequest httpRequest = null;
        HttpResponse httpResponse = null;

        try {
            // Http Method, URI, Host 정보를 획득한다
            httpRequest = HttpRequestParser.parseHttpRequest(socket.getInputStream());
            httpResponse = HttpResponse.builder()
                .outputStream(new PrintWriter(socket.getOutputStream(), true))
                .build();

            log.info(httpRequest.toString());

            if (StringUtils.isEmpty(httpRequest.getUri())) {
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            ResponseHandler.handleErrorResponse(httpRequest, serverSetting, socket, 500);
            return;
        }

        try {

            // Servlet Mapping 확인

            // 403, 404 여부 체크
            int errorCode = ValidationFilter.validate(httpRequest, serverSetting);

            if (errorCode != 200) {
                ResponseHandler.handleErrorResponse(httpRequest, serverSetting, socket, errorCode);
                return;
            }

            // File return
            ResponseHandler.handleFileResponse(httpRequest, serverSetting, socket);

        } catch (Exception e) {
            // 500 Error
            e.printStackTrace();
            ResponseHandler.handleErrorResponse(httpRequest, serverSetting, socket, 500);
        } finally {
            try {
                httpRequest.getInputStream().close();
                httpResponse.getOutputStream().close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

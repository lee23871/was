package com.thlee.work.http;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.thlee.work.model.HttpRequest;
import com.thlee.work.model.ServerSetting;
import com.thlee.work.model.ServerSetting.Host;
import com.thlee.work.util.FileUtil;
import com.thlee.work.util.HostUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResponseHandler {

    private static final String OUTPUT_HEADERS = "HTTP/1.1 %d\r\n" +
        "Content-Type: text/html\r\n" +
        "Content-Length: ";
    private static final String OUTPUT_END_OF_HEADERS = "\r\n\r\n";

    public static void handleFileResponse(HttpRequest httpRequest, ServerSetting serverSetting, Socket request) {
        Host hostInfo = HostUtil.getHost(httpRequest.getHost(), serverSetting);
        String filePath = hostInfo.getHttpRoot() + httpRequest.getUri().substring(1);

        String header = String.format(OUTPUT_HEADERS, 200);

        writeToStream(request, filePath, header);
    }


    /**
     * 403, 404, 500 등의 Error Response 를 생성한다.
     * @param httpRequest
     * @param serverSetting
     * @param request
     * @param errorCode
     */
    public static void handleErrorResponse(HttpRequest httpRequest, ServerSetting serverSetting, Socket request, int errorCode) {
        Host hostInfo = HostUtil.getHost(httpRequest.getHost(), serverSetting);

        String filePath = hostInfo.getHttpRoot();

        if (errorCode == 403) {
            filePath += hostInfo.getDoc403();
        } else if (errorCode == 404) {
            filePath += hostInfo.getDoc404();
        } else if (errorCode == 500) {
            filePath += hostInfo.getDoc500();
        } else {
            throw new RuntimeException("Undefined errorCode");
        }

        String header = String.format(OUTPUT_HEADERS, errorCode);

        writeToStream(request, filePath, header);
    }

    private static void writeToStream(Socket request, String filePath, String header) {
        try {
            BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(
                    new BufferedOutputStream(request.getOutputStream()), "UTF-8")
            );
            out.write(header + OUTPUT_END_OF_HEADERS + FileUtil.readFile(filePath));
            out.flush();
        } catch (FileNotFoundException ex) {
            log.error("File not found");
            ex.printStackTrace();
        } catch (IOException ioex) {
            log.error("Socket Exception");
            ioex.printStackTrace();
        }
    }
}

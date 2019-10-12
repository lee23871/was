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

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ErrorHandler {

    private static final String OUTPUT_HEADERS = "HTTP/1.1 %d\r\n" +
        "Content-Type: text/html\r\n" +
        "Content-Length: ";
    private static final String OUTPUT = "<html><head><title>Example</title></head><body><p>Worked!!!</p></body></html>";
    private static final String OUTPUT_END_OF_HEADERS = "\r\n\r\n";

    public static void handleErrorResponse(HttpRequest httpRequest, ServerSetting serverSetting, Socket request, int errorCode) {
        String host = httpRequest.getHost();
        Host hostInfo = serverSetting.getHosts().stream()
            .filter(h -> h.getName().equals(host))
            .findFirst()
            .orElse(null);

        if (hostInfo == null) {
            hostInfo = serverSetting.getHosts().stream()
                .filter(h -> h.getName().equals("default"))
                .findFirst()
                .orElse(null);
        }

        String filePath;

        if (errorCode == 403) {
            filePath = hostInfo.getDoc403();
        } else if (errorCode == 404) {
            filePath = hostInfo.getDoc404();
        } else if (errorCode == 500) {
            filePath = hostInfo.getDoc500();
        } else {
            throw new RuntimeException("Undefined errorCode");
        }

        try {
            BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(
                    new BufferedOutputStream(request.getOutputStream()), "UTF-8")
            );
            // native line endings are uncertain so add them manually
            out.write(String.format(OUTPUT_HEADERS, errorCode) + OUTPUT_END_OF_HEADERS + FileUtil.readFile(filePath));
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

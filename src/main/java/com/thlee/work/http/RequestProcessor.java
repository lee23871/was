package com.thlee.work.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import com.thlee.work.model.HttpRequest;
import com.thlee.work.model.ServerSetting;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestProcessor extends Thread {

    private static final String OUTPUT = "<html><head><title>Example</title></head><body><p>Worked!!!</p></body></html>";
    private static final String OUTPUT_HEADERS = "HTTP/1.1 200 OK\r\n" +
        "Content-Type: text/html\r\n" +
        "Content-Length: ";
    private static final String OUTPUT_END_OF_HEADERS = "\r\n\r\n";

    private ServerSetting serverSetting;
    private String indexFile;
    private Socket request;

    public RequestProcessor(ServerSetting serverSetting, String indexFile, Socket request) {
        this.serverSetting = serverSetting;
        this.indexFile = indexFile;
        this.request = request;
    }

    @Override
    public void run() {

        // Http Method, URI, Host 정보를 획득한다
        HttpRequest httpRequest = null;
        PrintWriter out;
        BufferedReader in;

        try {
            out = new PrintWriter(request.getOutputStream(), true);
            in = new BufferedReader(
                new InputStreamReader(request.getInputStream()));

            httpRequest = HttpRequestParser.parseHttpRequest(request.getInputStream());
            log.info(httpRequest.toString());
        } catch (IOException e) {
            e.printStackTrace();
            ErrorHandler.handleErrorResponse(httpRequest, serverSetting, request, 500);
            return;
        }

        try {

            // Servlet Mapping 확인

            // 403, 404 여부 체크

            ErrorHandler.handleErrorResponse(httpRequest, serverSetting, request, 403);

//            OutputStream outputStream = request.getOutputStream();
//            PrintWriter pw = new PrintWriter(outputStream, true);
//            // native line endings are uncertain so add them manually
//            pw.print("GET " + " HTTP/1.0\r\n");
//            pw.print("Accept: text/plain, text/html, text/*\r\n");
//            pw.print("\r\n");
//            pw.flush();

//            String httpResponse = "HTTP/1.1 200 OK\r\n\r\n";
//            request.getOutputStream().write(httpResponse.getBytes("UTF-8"));

//            OutputStream outputStream = request.getOutputStream();
//            BufferedWriter out = new BufferedWriter(
//                new OutputStreamWriter(
//                    new BufferedOutputStream(request.getOutputStream()), "UTF-8")
//            );
//            // native line endings are uncertain so add them manually
//            out.write(OUTPUT_HEADERS + OUTPUT.length() + OUTPUT_END_OF_HEADERS + OUTPUT);
//            out.flush();


            in.close();
            out.close();
            request.close();
        } catch (Exception e) {
            // 500 Error
            e.printStackTrace();
            ErrorHandler.handleErrorResponse(httpRequest, serverSetting, request, 500);
        }
    }
}

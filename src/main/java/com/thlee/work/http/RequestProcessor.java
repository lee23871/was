package com.thlee.work.http;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import com.thlee.work.model.HttpRequest;
import com.thlee.work.model.ServerSetting;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestProcessor implements Runnable {

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
        try {
            HttpRequest httpRequest = HttpRequestParser.parseHttpRequest(request);
            log.info(httpRequest.toString());

            OutputStream outputStream = request.getOutputStream();
            PrintWriter pw = new PrintWriter(outputStream, true);
            // native line endings are uncertain so add them manually
            pw.print("GET " + " HTTP/1.0\r\n");
            pw.print("Accept: text/plain, text/html, text/*\r\n");
            pw.print("\r\n");
            pw.flush();

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
            request.close();
        } catch (Exception e) {
            e.printStackTrace();

            OutputStream outputStream = null;
            try {
                outputStream = request.getOutputStream();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            PrintWriter pw = new PrintWriter(outputStream, false);
            // native line endings are uncertain so add them manually
            pw.print("GET " + " HTTP/1.0\r\n");
            pw.print("Accept: text/plain, text/html, text/*\r\n");
            pw.print("\r\n");
            pw.flush();
        }
    }
}

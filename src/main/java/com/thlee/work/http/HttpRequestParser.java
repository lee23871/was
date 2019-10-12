package com.thlee.work.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import com.thlee.work.model.HttpMethod;
import com.thlee.work.model.HttpRequest;
import com.thlee.work.util.StringUtils;

public class HttpRequestParser {

    public static HttpRequest parseHttpRequest(Socket request) throws IOException {
        InputStream theInput = request.getInputStream();

        InputStreamReader isr = new InputStreamReader(theInput);
        BufferedReader br = new BufferedReader(isr);
        HttpRequest.HttpRequestBuilder httpRequestBuilder = HttpRequest.builder();

        String line = br.readLine();
        if (line == null) {
            return HttpRequest.builder().build();
        }

        String[] requestInfo = line.split(" ");
        httpRequestBuilder.httpMethod(HttpMethod.valueOf(requestInfo[0]));
        httpRequestBuilder.uri(requestInfo[1]);

        while ((line = br.readLine()) != null) {
            if (StringUtils.isEmpty(line)) {
                continue;
            }

            if (line.startsWith("Host:")) {
                httpRequestBuilder.host(line.substring(line.indexOf(" ") + 1));
            }
        }

        return httpRequestBuilder.build();
    }
}

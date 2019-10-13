package com.thlee.work.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.thlee.work.model.HttpMethod;
import com.thlee.work.model.HttpRequest;
import com.thlee.work.util.StringUtils;

public class HttpRequestParser {

    /**
     * Socket 의 input stream 에서 Http Request 와 관련된 정보를 가져오는 Method
     * @param inputStream
     * @return
     */
    public static HttpRequest parseHttpRequest(InputStream inputStream) {
        HttpRequest.HttpRequestBuilder httpRequestBuilder = HttpRequest.builder();
        httpRequestBuilder.inputStream(new BufferedReader(
            new InputStreamReader(inputStream)));

        try {
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(isr);

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
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return httpRequestBuilder.build();
    }
}

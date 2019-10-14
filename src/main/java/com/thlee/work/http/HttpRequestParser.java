package com.thlee.work.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.thlee.work.model.HttpMethod;
import com.thlee.work.model.HttpRequest;
import com.thlee.work.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpRequestParser {

    /**
     * Socket 의 input stream 에서 Http Request 와 관련된 정보를 가져오는 Method
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

            if (requestInfo[1].contains("?")) {
                String paramStr = requestInfo[1].substring(requestInfo[1].indexOf("?") + 1);
                String[] paramArr = paramStr.split("&");
                Map<String, String> paramMap = new HashMap<>();
                for (String param : paramArr) {
                    paramMap.put(param.substring(0, param.indexOf("=")),
                        param.substring(param.indexOf("=") + 1));
                }
                httpRequestBuilder.parameters(paramMap);
                httpRequestBuilder.uri(requestInfo[1].substring(0, requestInfo[1].indexOf("?")));
            } else {
                httpRequestBuilder.parameters(new HashMap<>());
                httpRequestBuilder.uri(requestInfo[1]);
            }

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
            log.error("Exception: ", e);
        }

        return httpRequestBuilder.build();
    }
}

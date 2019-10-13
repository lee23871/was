package com.thlee.work.model;

import java.io.BufferedReader;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class HttpRequest {

    private HttpMethod httpMethod;
    private String uri;
    private String host;
    private BufferedReader inputStream;
    private Map<String, String> parameters;

    public String getParameter(String key) {
        return parameters.getOrDefault(key, "");
    }
}

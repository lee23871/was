package com.thlee.work.model;

import java.io.BufferedReader;

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
}

package com.thlee.work.model;

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
}

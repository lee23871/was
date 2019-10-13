package com.thlee.work.model;

import java.io.Writer;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HttpResponse {

    private Writer outputStream;
}

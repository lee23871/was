package com.thlee.work.http;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;

import com.thlee.work.model.HttpRequest;

import static org.junit.Assert.*;

public class HttpRequestParserTest {

    @Test
    public void parseHttpRequest() {
        // Arrange
        InputStream inputStream = new ByteArrayInputStream("GET /Hello?name=abc\nHost: localhost".getBytes());

        // Act
        HttpRequest httpRequest = HttpRequestParser.parseHttpRequest(inputStream);

        // Assert
        assertEquals("localhost", httpRequest.getHost());
        assertEquals("/Hello", httpRequest.getUri());
        assertEquals("abc", httpRequest.getParameter("name"));
    }
}

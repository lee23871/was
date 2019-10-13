package com.thlee.work.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.thlee.work.model.ServerSetting;
import com.thlee.work.model.ServerSetting.Host;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RequestProcessorTest {

    private ServerSetting serverSetting;
    private Socket socket;
    private OutputStream outputStream;

    @Before
    public void before() throws IOException {
        serverSetting = getServerSetting();
        outputStream = new ByteArrayOutputStream();

        socket = mock(Socket.class);
        when(socket.getOutputStream()).thenReturn(outputStream);
    }

    @Test
    public void helloTest() throws IOException {
        // Arrange
        InputStream inputStream = new ByteArrayInputStream("GET /Hello?name=abc\nHost:localhost".getBytes());
        when(socket.getInputStream()).thenReturn(inputStream);
        RequestProcessor requestProcessor = new RequestProcessor(serverSetting, socket);

        // Act
        requestProcessor.run();

        // Assert
        String result = outputStream.toString();
        assertEquals("HTTP/1.1 200\r\n\r\nHello, abc", result);
    }

    @Test
    public void serviceHelloTest() throws IOException {
        // Arrange
        InputStream inputStream = new ByteArrayInputStream("GET /service.Hello?name=abc\nHost:localhost".getBytes());
        when(socket.getInputStream()).thenReturn(inputStream);
        RequestProcessor requestProcessor = new RequestProcessor(serverSetting, socket);

        // Act
        requestProcessor.run();

        // Assert
        String result = outputStream.toString();
        assertEquals("HTTP/1.1 200\r\n\r\nService Hello, abc", result);
    }

    @Test
    public void error403Test() throws IOException {
        // Arrange
        InputStream inputStream = new ByteArrayInputStream("GET /../../etc\nHost:localhost".getBytes());
        when(socket.getInputStream()).thenReturn(inputStream);
        RequestProcessor requestProcessor = new RequestProcessor(serverSetting, socket);

        // Act
        requestProcessor.run();

        // Assert
        String result = outputStream.toString();
        assertEquals("HTTP/1.1 403\r\nContent-Type: text/html\r\nContent-Length: \r\n\r\n403 Error!!", result);
    }

    @Test
    public void error404Test() throws IOException {
        // Arrange
        InputStream inputStream = new ByteArrayInputStream("GET /abc\nHost:localhost".getBytes());
        when(socket.getInputStream()).thenReturn(inputStream);
        RequestProcessor requestProcessor = new RequestProcessor(serverSetting, socket);

        // Act
        requestProcessor.run();

        // Assert
        String result = outputStream.toString();
        assertEquals("HTTP/1.1 404\r\nContent-Type: text/html\r\nContent-Length: \r\n\r\n404 Error!!", result);
    }

    @Test
    public void fileTest() throws IOException {
        // Arrange
        InputStream inputStream = new ByteArrayInputStream("GET /test.txt\nHost:localhost".getBytes());
        when(socket.getInputStream()).thenReturn(inputStream);
        RequestProcessor requestProcessor = new RequestProcessor(serverSetting, socket);

        // Act
        requestProcessor.run();

        // Assert
        String result = outputStream.toString();
        assertEquals("HTTP/1.1 200\r\nContent-Type: text/html\r\nContent-Length: \r\n\r\ntest", result);
    }

    @Test
    public void fileDifferentHostTest() throws IOException {
        // Arrange
        InputStream inputStream = new ByteArrayInputStream("GET /test.txt\nHost: a.com".getBytes());
        when(socket.getInputStream()).thenReturn(inputStream);
        RequestProcessor requestProcessor = new RequestProcessor(serverSetting, socket);

        // Act
        requestProcessor.run();

        // Assert
        String result = outputStream.toString();
        assertEquals("HTTP/1.1 200\r\nContent-Type: text/html\r\nContent-Length: \r\n\r\na: test", result);
    }

    private ServerSetting getServerSetting() {
        ServerSetting serverSetting = new ServerSetting();
        serverSetting.setPort(80);

        Host defaultHost = new Host();
        defaultHost.setName("default");
        defaultHost.setDoc403("403.html");
        defaultHost.setDoc404("404.html");
        defaultHost.setDoc500("500.html");
        defaultHost.setHttpRoot("src/main/resources/");

        Host hostA = new Host();
        hostA.setName("a.com");
        hostA.setDoc403("403.html");
        hostA.setDoc404("404.html");
        hostA.setDoc500("500.html");
        hostA.setHttpRoot("src/main/resources/a/");

        serverSetting.setHosts(Arrays.asList(defaultHost, hostA));
        return serverSetting;
    }
}
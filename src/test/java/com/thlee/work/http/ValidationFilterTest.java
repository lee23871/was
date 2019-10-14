package com.thlee.work.http;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.thlee.work.model.HttpRequest;
import com.thlee.work.model.ServerSetting;
import com.thlee.work.model.ServerSetting.Host;

import static org.junit.Assert.*;

public class ValidationFilterTest {

    private ServerSetting serverSetting;

    @Before
    public void before() {
        serverSetting = getServerSetting();
    }

    @Test
    public void validate403() {
        // Arrange
        HttpRequest httpRequest = HttpRequest.builder()
            .uri("/../../etc/passwd")
            .build();

        // Act
        int errorCode = ValidationFilter.validate(httpRequest, serverSetting);

        // Assert
        assertEquals(403, errorCode);
    }

    @Test
    public void validate403_2() {
        // Arrange
        HttpRequest httpRequest = HttpRequest.builder()
            .uri("/a.exe")
            .build();

        // Act
        int errorCode = ValidationFilter.validate(httpRequest, serverSetting);

        // Assert
        assertEquals(403, errorCode);
    }

    @Test
    public void validate404() {
        // Arrange
        HttpRequest httpRequest = HttpRequest.builder()
            .uri("/abc")
            .build();

        // Act
        int errorCode = ValidationFilter.validate(httpRequest, serverSetting);

        // Assert
        assertEquals(404, errorCode);
    }

    @Test
    public void validate200() {
        // Arrange
        HttpRequest httpRequest = HttpRequest.builder()
            .uri("/test.txt")
            .build();

        // Act
        int errorCode = ValidationFilter.validate(httpRequest, serverSetting);

        // Assert
        assertEquals(200, errorCode);
    }

    private ServerSetting getServerSetting() {
        ServerSetting serverSetting = new ServerSetting();
        serverSetting.setPort(80);

        Host defaultHost = new Host();
        defaultHost.setName("default");
        defaultHost.setDoc403("403.html");
        defaultHost.setDoc404("404.html");
        defaultHost.setDoc500("500.html");
        defaultHost.setHttpRoot("");

        Host hostA = new Host();
        hostA.setName("a.com");
        hostA.setDoc403("403.html");
        hostA.setDoc404("404.html");
        hostA.setDoc500("500.html");
        hostA.setHttpRoot("a/");

        serverSetting.setHosts(Arrays.asList(defaultHost, hostA));
        return serverSetting;
    }
}
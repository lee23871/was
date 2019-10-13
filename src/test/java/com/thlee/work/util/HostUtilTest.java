package com.thlee.work.util;

import java.util.Arrays;

import org.junit.Test;

import com.thlee.work.model.ServerSetting;
import com.thlee.work.model.ServerSetting.Host;

import static org.junit.Assert.*;

public class HostUtilTest {

    @Test
    public void getHost() {
        // Arrange
        ServerSetting serverSetting = getServerSetting();

        // Act
        Host host = HostUtil.getHost("a.com", serverSetting);

        //
        assertEquals("a.com", host.getName());
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

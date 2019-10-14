package com.thlee.work;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thlee.work.http.RequestProcessor;
import com.thlee.work.model.ServerSetting;
import com.thlee.work.util.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpServer {

    private static final int NUM_THREADS = 50;
    private final ServerSetting serverSetting;
    private final int port;

    public HttpServer(ServerSetting serverSetting, int port) throws IOException {
        this.serverSetting = serverSetting;
        this.port = port;
    }

    public void start() throws IOException {
        ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);
        try (ServerSocket server = new ServerSocket(port)) {
            log.info("Accepting connections on port " + server.getLocalPort());
            while (true) {
                try {
                    Socket request = server.accept();
                    Runnable r = new RequestProcessor(serverSetting, request);
                    pool.submit(r);
                } catch (IOException ex) {
                    log.error("Error accepting connection", ex);
                }
            }
        }
    }

    public static void main(String[] args) {
        // get the Document root
        ServerSetting serverSetting = getSetting();

        // set the port to listen on
        int port;
        try {
            port = serverSetting.getPort();
            if (port < 0 || port > 65535) {
                port = 80;
            }
        } catch (RuntimeException ex) {
            port = 80;
        }
        try {
            HttpServer webserver = new HttpServer(serverSetting, port);
            webserver.start();
        } catch (IOException ex) {
            log.error("Server could not start", ex);
        }
    }

    private static ServerSetting getSetting() {
        ObjectMapper objectMapper = new ObjectMapper();
        String settingStr = readFromSettingFile();

        try {
            return objectMapper.readValue(settingStr, ServerSetting.class);
        } catch (JsonProcessingException e) {
            log.error("Setting file error");
        }

        return null;
    }

    private static String readFromSettingFile() {
        return FileUtil.readFile("server.setting");
    }
}

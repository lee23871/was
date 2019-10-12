package com.thlee.work;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thlee.work.http.RequestProcessor;
import com.thlee.work.model.ServerSetting;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpServer {
    private static final Logger logger = Logger.getLogger(HttpServer.class.getCanonicalName());
    private static final int NUM_THREADS = 50;
    private static final String INDEX_FILE = "index.html";
    private final ServerSetting serverSetting;
    private final int port;

    public HttpServer(ServerSetting serverSetting, int port) throws IOException {
        this.serverSetting = serverSetting;
        this.port = port;
    }

    public void start() throws IOException {
        ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);
        try (ServerSocket server = new ServerSocket(port)) {
            logger.info("Accepting connections on port " + server.getLocalPort());
            while (true) {
                try {
                    Socket request = server.accept();
                    Runnable r = new RequestProcessor(serverSetting, INDEX_FILE, request);
                    pool.submit(r);
                } catch (IOException ex) {
                    logger.log(Level.WARNING, "Error accepting connection", ex);
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
            if (port < 0 || port > 65535) port = 80;
        } catch (RuntimeException ex) {
            port = 80;
        }
        try {
            HttpServer webserver = new HttpServer(serverSetting, port);
            webserver.start();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Server could not start", ex);
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
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = Files.newBufferedReader(Paths.get("src/main/resources/server.setting"))) {

            // read line by line
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            log.error("IOException: %s%n", e);
        }

        return sb.toString();
    }
}
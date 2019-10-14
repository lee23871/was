package com.thlee.work.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtil {

    public static String readFile(String path) {

        ResourceHelper resourceHelper = new ResourceHelper();
        InputStream fileInputStream = resourceHelper.getResourceFile(path);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"))) {

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

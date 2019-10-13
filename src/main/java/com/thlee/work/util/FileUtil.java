package com.thlee.work.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtil {

    public static String readFile(String path) {

        ResourceHelper resourceHelper = new ResourceHelper();
        File file = resourceHelper.getResourceFile(path);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(file.getPath()))) {

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

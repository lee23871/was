package com.thlee.work.util;

import java.io.InputStream;

public class ResourceHelper {

    /**
     * Resource File 을 읽어서 InputStream 을 반환한다.
     * @param filePath
     * @return
     */
    public InputStream getResourceFile(String filePath) {
        return getClass().getClassLoader().getResourceAsStream(filePath);
    }
}

package com.thlee.work.util;

import java.io.InputStream;

public class ResourceHelper {

    public InputStream getResourceFile(String filePath) {
        return getClass().getClassLoader().getResourceAsStream(filePath);
    }
}

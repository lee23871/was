package com.thlee.work.util;

import java.io.File;

public class ResourceHelper {

    public File getResourceFile(String filePath) {
        return new File(
            getClass().getClassLoader().getResource(filePath).getFile()
        );
    }
}

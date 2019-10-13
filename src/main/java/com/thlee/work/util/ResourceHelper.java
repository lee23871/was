package com.thlee.work.util;

import java.io.File;
import java.net.URL;

public class ResourceHelper {

    public File getResourceFile(String filePath) {
        URL resource = getClass().getClassLoader().getResource(filePath);
        if (resource == null) {
            return null;
        }

        return new File(resource.getFile());
    }
}

package com.thlee.work.servlet;


import java.util.HashMap;
import java.util.Map;

public class ServletMapping {

    private static Map<String, String> mapping;

    static {
        mapping = new HashMap<>();
        mapping.put("/Hello", "Hello");
        mapping.put("/service.Hello", "service.Hello");
    }

    public static String findMapping(String uri) {
        return mapping.getOrDefault(uri, "");
    }
}

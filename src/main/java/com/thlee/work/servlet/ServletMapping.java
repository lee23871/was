package com.thlee.work.servlet;


import java.util.HashMap;
import java.util.Map;

public class ServletMapping {

    private static Map<String, String> mapping;

    // 추후 설정 파일로 아래의 내용을 관리하도록 수정 가능
    static {
        mapping = new HashMap<>();
        mapping.put("/Hello", "Hello");
        mapping.put("/service.Hello", "service.Hello");
        mapping.put("/service.Time", "service.TimeServlet");
    }

    public static String findMapping(String uri) {
        return mapping.getOrDefault(uri, "");
    }
}

package com.thlee.work.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.thlee.work.model.HttpRequest;
import com.thlee.work.model.HttpResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReflectionUtil {

    private static final String BASE_PACKAGE = "com.thlee.work.";
    public static final String METHOD_NAME = "service";

    public static void invoke(String classPath, HttpRequest httpRequest, HttpResponse httpResponse) {
        Class<?> c;
        try {
            c = Class.forName(BASE_PACKAGE + classPath);
            Method method = c.getMethod(METHOD_NAME, HttpRequest.class, HttpResponse.class);
            method.invoke(c.newInstance(), httpRequest, httpResponse);
        } catch (ClassNotFoundException e) {
            log.error("ClassNotFoundException");
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            log.error("NoSuchMethodException");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            log.error("IllegalAccessException");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            log.error("InvocationTargetException");
            e.printStackTrace();
        } catch (InstantiationException e) {
            log.error("InstantiationException");
            e.printStackTrace();
        }
    }
}

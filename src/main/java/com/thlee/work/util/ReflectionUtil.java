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

    /**
     * 해당 Class 의 service method 를 실행한다.
     * @param classPath
     * @param httpRequest
     * @param httpResponse
     */
    public static void invoke(String classPath, HttpRequest httpRequest, HttpResponse httpResponse) {
        Class<?> c;
        try {
            c = Class.forName(BASE_PACKAGE + classPath);
            Method method = c.getMethod(METHOD_NAME, HttpRequest.class, HttpResponse.class);
            method.invoke(c.newInstance(), httpRequest, httpResponse);
        } catch (ClassNotFoundException e) {
            log.error("ClassNotFoundException: ", e);
        } catch (NoSuchMethodException e) {
            log.error("NoSuchMethodException: ", e);
        } catch (IllegalAccessException e) {
            log.error("IllegalAccessException: ", e);
        } catch (InvocationTargetException e) {
            log.error("InvocationTargetException: ", e);
        } catch (InstantiationException e) {
            log.error("InstantiationException: ", e);
        }
    }
}

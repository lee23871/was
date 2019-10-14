package com.thlee.work.service;

import java.io.IOException;
import java.io.Writer;

import com.thlee.work.model.HttpRequest;
import com.thlee.work.model.HttpResponse;
import com.thlee.work.servlet.SimpleServlet;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Hello implements SimpleServlet {

    @Override
    public void service(HttpRequest req, HttpResponse res) {
        Writer writer = res.getOutputStream();
        try {
            writer.write("Service Hello, ");
            writer.write(req.getParameter("name"));
        } catch (IOException e) {
            log.error("Hello Service Error: ", e);
        }
    }
}

package com.thlee.work;

import java.io.IOException;
import java.io.Writer;

import com.thlee.work.model.HttpRequest;
import com.thlee.work.model.HttpResponse;
import com.thlee.work.servlet.SimpleServlet;

public class Hello implements SimpleServlet {
    @Override
    public void service(HttpRequest req, HttpResponse res) {
        Writer writer = res.getOutputStream();
        try {
            writer.write("Hello, ");
            //writer.write(req.getParameter("name"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

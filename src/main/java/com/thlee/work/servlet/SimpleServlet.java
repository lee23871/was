package com.thlee.work.servlet;

import com.thlee.work.model.HttpRequest;
import com.thlee.work.model.HttpResponse;

public interface SimpleServlet {

    void service(HttpRequest req, HttpResponse res);
}

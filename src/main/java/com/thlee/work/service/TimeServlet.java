package com.thlee.work.service;

import java.io.IOException;
import java.io.Writer;
import java.util.Calendar;

import com.thlee.work.model.HttpRequest;
import com.thlee.work.model.HttpResponse;
import com.thlee.work.servlet.SimpleServlet;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeServlet implements SimpleServlet {

    @Override
    public void service(HttpRequest req, HttpResponse res) {
        Writer writer = res.getOutputStream();
        try {
            Calendar cal = Calendar.getInstance();
            writer.write(cal.get(Calendar.HOUR_OF_DAY)
                + ":" + cal.get(Calendar.MINUTE)
                + ":" + cal.get(Calendar.SECOND));
        } catch (IOException e) {
            log.error("Time Service Error: ", e);
        }
    }
}

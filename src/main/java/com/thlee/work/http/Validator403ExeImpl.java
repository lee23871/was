package com.thlee.work.http;

import com.thlee.work.model.HttpRequest;
import com.thlee.work.model.ServerSetting;

public class Validator403ExeImpl implements Validator {

    @Override
    public boolean validate(HttpRequest httpRequest, ServerSetting serverSetting) {
        if (httpRequest == null || httpRequest.getUri() == null) {
            return true;
        }

        return !httpRequest.getUri().endsWith(".exe");
    }
}

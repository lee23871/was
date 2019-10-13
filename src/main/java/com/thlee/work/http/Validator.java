package com.thlee.work.http;

import com.thlee.work.model.HttpRequest;
import com.thlee.work.model.ServerSetting;

public interface Validator {

    boolean validate(HttpRequest httpRequest, ServerSetting serverSetting);
}

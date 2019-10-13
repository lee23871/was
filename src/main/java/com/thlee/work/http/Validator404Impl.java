package com.thlee.work.http;

import java.io.File;

import com.thlee.work.model.HttpRequest;
import com.thlee.work.model.ServerSetting;
import com.thlee.work.model.ServerSetting.Host;
import com.thlee.work.util.HostUtil;

public class Validator404Impl implements Validator {

    @Override
    public boolean validate(HttpRequest httpRequest, ServerSetting serverSetting) {
        if (httpRequest == null || httpRequest.getUri() == null) {
            return true;
        }

        Host hostInfo = HostUtil.getHost(httpRequest, serverSetting);
        File file = new File(hostInfo.getHttpRoot() + httpRequest.getUri());
        return file.exists();
    }
}

package com.thlee.work.http;

import java.io.InputStream;

import com.thlee.work.model.HttpRequest;
import com.thlee.work.model.ServerSetting;
import com.thlee.work.model.ServerSetting.Host;
import com.thlee.work.util.HostUtil;
import com.thlee.work.util.ResourceHelper;

public class Validator404Impl implements Validator {

    @Override
    public boolean validate(HttpRequest httpRequest, ServerSetting serverSetting) {
        if (httpRequest == null || httpRequest.getUri() == null) {
            return true;
        }

        ResourceHelper resourceHelper = new ResourceHelper();
        Host hostInfo = HostUtil.getHost(httpRequest.getHost(), serverSetting);
        InputStream inputStream = resourceHelper
            .getResourceFile(hostInfo.getHttpRoot() + httpRequest.getUri().substring(1));
        return inputStream != null;
    }
}

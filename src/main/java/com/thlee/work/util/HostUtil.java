package com.thlee.work.util;

import com.thlee.work.model.ServerSetting;
import com.thlee.work.model.ServerSetting.Host;

public class HostUtil {

    public static Host getHost(String host, ServerSetting serverSetting) {
        Host hostInfo = serverSetting.getHosts().stream()
            .filter(h -> h.getName().equals(host))
            .findFirst()
            .orElse(null);

        if (hostInfo == null) {
            hostInfo = serverSetting.getHosts().stream()
                .filter(h -> h.getName().equals("default"))
                .findFirst()
                .orElse(null);
        }

        return hostInfo;
    }
}

package com.thlee.work.util;

import com.thlee.work.model.ServerSetting;
import com.thlee.work.model.ServerSetting.Host;

public class HostUtil {

    /**
     * ServerSetting 에서 해당하는 host 를 반환하고 없으면 default host 를 반환한다.
     * @param host
     * @param serverSetting
     * @return
     */
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

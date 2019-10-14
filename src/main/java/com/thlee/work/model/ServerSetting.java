package com.thlee.work.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerSetting {

    private int port;
    private List<Host> hosts;

    @Getter
    @Setter
    public static class Host {

        private String name;
        private String httpRoot;
        private String doc403;
        private String doc404;
        private String doc500;
    }
}

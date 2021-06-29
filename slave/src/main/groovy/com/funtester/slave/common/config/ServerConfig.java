package com.funtester.slave.common.config;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ServerConfig implements ApplicationListener<WebServerInitializedEvent> {

    public static int serverPort;

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        serverPort = event.getWebServer().getPort();

    }


}

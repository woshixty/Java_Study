package com.sailing.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/11/22
 * socket配置类,往 spring 容器中注入ServerEndpointExporter实例
 **/

@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}

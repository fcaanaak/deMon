package com.modesec.server.websocket.config;

import com.modesec.server.websocket.handler.DeviceMessagingHandler;
import com.modesec.server.websocket.handler.ReportUpdatesHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@EnableWebSocket
public class WsConfig implements WebSocketConfigurer {

    @Autowired
    private DeviceMessagingHandler deviceMessagingHandler;

    @Autowired
    private ReportUpdatesHandler reportUpdatesHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(deviceMessagingHandler, "/ws")
                .setAllowedOrigins("http://localhost:8080/");

        registry.addHandler(reportUpdatesHandler, "/ws-reports")
                .setAllowedOrigins("http://localhost:8080/");
    }
}

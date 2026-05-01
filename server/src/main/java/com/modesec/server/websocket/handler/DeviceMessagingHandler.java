package com.modesec.server.websocket.handler;

import com.modesec.server.models.ArmRequest;
import com.modesec.server.repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DeviceMessagingHandler extends TextWebSocketHandler {

    public final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private DeviceRepository deviceRepository;

    private void bootstrapConnection(WebSocketSession session) {

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        session.sendMessage(
                new TextMessage("Echo (from devices):" + payload)
        );
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    public void broadcastArmingToggle(ArmRequest armRequest) throws IOException {

        for (WebSocketSession session: sessions) {
            session.sendMessage(
                    new TextMessage(objectMapper.writeValueAsString(armRequest))
            );
        }
    }


}

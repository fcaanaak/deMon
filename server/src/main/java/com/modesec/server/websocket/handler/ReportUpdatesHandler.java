package com.modesec.server.websocket.handler;

import com.modesec.server.models.Report;
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

/**
 * Websocket handler used to update clients whenever device reports come in
 *
 */

@Component
public class ReportUpdatesHandler extends TextWebSocketHandler {

    ObjectMapper objectMapper = new ObjectMapper();

    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        session.sendMessage(
                new TextMessage("Echo (Reports):" + payload)
        );
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    public void broadcastReportToClients(Report report) throws IOException {

        for (WebSocketSession client: sessions) {
            client.sendMessage(
                    new TextMessage(objectMapper.writeValueAsString(report))
            );
        }

    }

}

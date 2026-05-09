package com.modesec.server.websocket.handler;

import com.modesec.server.models.ArmRequest;
import com.modesec.server.models.Device;
import com.modesec.server.models.DeviceMessage;
import com.modesec.server.models.DeviceSessionContainer;
import com.modesec.server.repositories.DeviceRepository;
import com.modesec.server.services.DeviceInitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DeviceMessagingHandler extends TextWebSocketHandler {

    public final Set<WebSocketSession> pendingSessions = ConcurrentHashMap.newKeySet();
    private final Map<String, DeviceSessionContainer> deviceSessions = new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceInitService deviceInitService;

    private Boolean checkForInit(DeviceMessage deviceMessage) {
        return deviceMessage.isInit();
    }

    private void deviceInit(WebSocketSession session, DeviceMessage deviceMessage) {

        if (pendingSessions.contains(session)) {
            pendingSessions.remove(session);
            Device initializedDevice = deviceInitService.deviceInit(deviceMessage);
            deviceSessions.putIfAbsent(session.getId(), new DeviceSessionContainer(initializedDevice, session));
        }

    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        pendingSessions.add(session);
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String payload = message.getPayload();
        DeviceMessage payloadMessage = objectMapper.readValue(payload, DeviceMessage.class);

        if (checkForInit(payloadMessage)) {
            deviceInit(session, payloadMessage);
        }

        // For debugging, remove later
        session.sendMessage(
                new TextMessage("Echo (from devices):" + payload)
        );
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        DeviceSessionContainer foundContainer = deviceSessions.getOrDefault(session.getId(), null);

        if (foundContainer != null) {
            Device foundDevice = foundContainer.device();
            foundDevice.setOnline(false);
            deviceRepository.save(foundDevice);
        }

        deviceSessions.remove(session.getId());
        pendingSessions.remove(session);// Just in case
    }


    public void broadcastArmingToggle(ArmRequest armRequest) throws IOException {

        for (WebSocketSession session: pendingSessions) {
            session.sendMessage(
                    new TextMessage(objectMapper.writeValueAsString(armRequest))
            );
        }
    }


}

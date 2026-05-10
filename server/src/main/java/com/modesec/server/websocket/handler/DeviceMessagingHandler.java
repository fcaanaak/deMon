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

    /**
     * Check if a device message indicates a device needs to be initialized
     *
     * @param deviceMessage The message to check
     * @return True if the device needs to be initialized and false otherwise
     */
    private Boolean checkForInit(DeviceMessage deviceMessage) {
        return deviceMessage.isInit();
    }

    /**
     * Initialize a device on the server side
     *
     * @param session The websocket session for the device
     * @param deviceMessage The message sent by the device that triggered init
     */
    private void deviceInit(WebSocketSession session, DeviceMessage deviceMessage) {

        if (pendingSessions.contains(session)) {
            pendingSessions.remove(session);

            Device initializedDevice = deviceInitService.deviceInit(deviceMessage);
            deviceSessions.putIfAbsent(session.getId(), new DeviceSessionContainer(initializedDevice, session));
        }
        // Do nothing in the event that an already registered device sends an init message
    }

    /**
     * Remove the device-session container so its no longer tracked and remove the session from pending if it was
     *
     * @param session The websocket session for this device
     */
    private void removeDeviceAndSession(WebSocketSession session) {
        deviceSessions.remove(session.getId());
        pendingSessions.remove(session);
    }


    /**
     * Set a device to be offline and
     *
     * @param device
     */
    private void setDeviceOffline(Device device) {
        device.setOnline(false);
        deviceRepository.save(device);
    }

    /**
     * Deal with the event that a device unexpectedly (or expectedly) goes offline
     *
     * @param session The websocket session for the device
     */
    private void handleDeviceDisconnect(WebSocketSession session) {

        DeviceSessionContainer foundContainer = deviceSessions.getOrDefault(session.getId(), null);

        if (foundContainer != null) {
            setDeviceOffline(foundContainer.device());
        }

        removeDeviceAndSession(session);
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
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        handleDeviceDisconnect(session);
    }


    public void broadcastArmingToggle(ArmRequest armRequest) {

        for (DeviceSessionContainer devSesh: deviceSessions.values()) {
            try {
                devSesh.session().sendMessage(new TextMessage(objectMapper.writeValueAsString(armRequest)));
            } catch (IOException e) {

                try {
                    devSesh.session().close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex); // Might want to display something on the client-side here
                }

            }

        }
    }


}

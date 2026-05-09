package com.modesec.server.models;

import org.springframework.web.socket.WebSocketSession;

public record DeviceSessionContainer(Device device, WebSocketSession session) {
}

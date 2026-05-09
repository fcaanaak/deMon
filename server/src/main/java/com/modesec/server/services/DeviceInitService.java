package com.modesec.server.services;

import com.modesec.server.models.Device;
import com.modesec.server.models.DeviceMessage;
import org.springframework.web.socket.WebSocketSession;

public interface DeviceInitService {

    Device deviceInit(DeviceMessage deviceMessage);
}

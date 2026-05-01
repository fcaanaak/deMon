package com.modesec.server.controllers;


import com.modesec.server.controllers.constants.CoreConstants;
import com.modesec.server.models.ArmRequest;
import com.modesec.server.websocket.handler.DeviceMessagingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ArmingController {

    Logger logger = LoggerFactory.getLogger(ArmingController.class);

    @Autowired
    private DeviceMessagingHandler deviceMessagingHandler;


    @PutMapping(CoreConstants.ARMING_ENDPOINT)
    public ArmRequest toggleArming(@RequestBody ArmRequest armRequest) {

        try {
            deviceMessagingHandler.broadcastArmingToggle(armRequest);
            logger.info(deviceMessagingHandler.sessions.toString());
        } catch (IOException e) {
            // Do nothing for now
        }

        return armRequest;


    }
}

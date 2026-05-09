package com.modesec.server.controllers;

import com.modesec.server.controllers.constants.CoreConstants;
import com.modesec.server.models.ArmRequest;
import com.modesec.server.services.DeviceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArmingController {

    @Autowired
    private DeviceService deviceService;

    @PutMapping(CoreConstants.ARMING_ENDPOINT)
    public ArmRequest toggleArming(@RequestBody ArmRequest armRequest) {

        deviceService.setArmed(armRequest);
        return armRequest;

    }
}

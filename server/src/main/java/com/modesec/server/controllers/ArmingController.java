package com.modesec.server.controllers;

import com.modesec.server.controllers.constants.CoreConstants;
import com.modesec.server.models.ArmRequest;
import com.modesec.server.services.DeviceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * Rest controller for arming endpoint
 * to be used by clients
 */
@RestController
public class ArmingController {

    @Autowired
    private DeviceService deviceService;

    /**
     * Mapping for PUT method
     * broadcasts the arming request to all devices
     *
     * @param armRequest a JSON containing the arming request and who it came from (to be added)
     * @returns the arming request passed in
     *
     */
    @PutMapping(CoreConstants.ARMING_ENDPOINT)
    public ArmRequest toggleArming(@RequestBody ArmRequest armRequest) {

        deviceService.setArmed(armRequest);
        return armRequest;

    }
}

package com.modesec.server.controllers;

import com.modesec.server.controllers.constants.CoreConstants;
import com.modesec.server.models.Device;
import com.modesec.server.services.DeviceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class DeviceHealthController {

    @Autowired
    DeviceServiceImpl deviceHealthService;

    /**
     * Get all the devices that are currently down
     *
     * @return a list of downed device or an empty list if no devices are down
     */
    @GetMapping(CoreConstants.DEVICE_HEALTH_ENDPOINT)
    public List<Device> getInactiveDevices() {

        return deviceHealthService.checkDevices();

    }
}

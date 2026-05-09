package com.modesec.server.controllers;

import com.modesec.server.controllers.constants.CoreConstants;
import com.modesec.server.models.Device;
import com.modesec.server.services.DeviceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Endpoint meant to be used by clients only to
 * retrieve the health status of the currently enrolled devices
 */
@RestController
public class DeviceHealthController {

    @Autowired
    DeviceServiceImpl deviceService;

    /**
     * Get all the devices that are currently offline
     *
     * @return a list of downed device or an empty list if no devices are down
     */
    @GetMapping(CoreConstants.DEVICE_HEALTH_ENDPOINT)
    public List<Device> getInactiveDevices() {
        return deviceService.checkDevices();
    }
}

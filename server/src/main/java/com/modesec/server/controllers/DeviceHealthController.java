package com.modesec.server.controllers;

import com.modesec.server.controllers.constants.CoreConstants;
import com.modesec.server.models.Device;
import com.modesec.server.services.DeviceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
     * Get all devices
     *
     * @return a list of all registered devices
     */
    @GetMapping(CoreConstants.DEVICE_HEALTH_ENDPOINT)
    @CrossOrigin(origins = CoreConstants.FRONTEND_URL)
    public List<Device> getDevices() {
        return deviceService.getDevices();
    }

    /**
     * Get all the devices that are offline
     * @return a list of all offline devices
     */
    @GetMapping(CoreConstants.DOWNED_DEVICE_ENDPOINT)
    @CrossOrigin(origins = CoreConstants.FRONTEND_URL)
    public Integer getNumberOfDownedDevices() {
        return deviceService.getNumberOfDownedDevices();
    }
}

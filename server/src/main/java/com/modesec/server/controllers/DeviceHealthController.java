package com.modesec.server.controllers;

import com.modesec.server.controllers.constants.CoreConstants;
import com.modesec.server.controllers.exceptions.ResourceNotFoundException;
import com.modesec.server.models.Device;
import com.modesec.server.services.DeviceServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST Endpoint meant to be used by clients only to
 * retrieve the health status of the currently enrolled devices
 */
@RestController
public class DeviceHealthController {

    @Autowired
    DeviceServiceImpl deviceService;

    private static Logger logger = LoggerFactory.getLogger(DeviceHealthController.class);
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

    /**
     * Remove a specific device given its UUID
      */
    @DeleteMapping(CoreConstants.DEVICE_HEALTH_ENDPOINT + "/{deviceId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @CrossOrigin(origins = CoreConstants.FRONTEND_URL)
    public void deleteDeviceFromUUID(@PathVariable UUID deviceId) {
        Device deletedDevice = deviceService.deleteDeviceFromUUID(deviceId);
        logger.debug(String.valueOf(deviceId));
        if (deletedDevice == null) {
            throw new ResourceNotFoundException("Device with ID " + deviceId + " was not found");
        }

    }
}

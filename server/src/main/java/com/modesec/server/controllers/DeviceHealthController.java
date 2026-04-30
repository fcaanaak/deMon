package com.modesec.server.controllers;

import com.modesec.server.services.DeviceHealthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DeviceHealthController {

    @Autowired
    DeviceHealthServiceImpl deviceHealthService;

    /**
     * Get all the devices that are currently down
     *
     * @return a list of downed device or an empty list if no devices are down
     */
    @GetMapping("/device-health")
    public List<Long> getInactiveDevices() {

        return new ArrayList<>();// Placeholder

    }
}

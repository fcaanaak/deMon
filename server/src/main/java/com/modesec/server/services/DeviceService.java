package com.modesec.server.services;

import com.modesec.server.models.ArmRequest;
import com.modesec.server.models.Device;

import java.util.List;

/**
 * Service concerned with operating on devices
 */
public interface DeviceService {

    List<Device> getDevices();
    Integer getNumberOfDownedDevices();

    void setArmed(ArmRequest armRequest);

}

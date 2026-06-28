package com.modesec.server.services;

import com.modesec.server.models.ArmRequest;
import com.modesec.server.models.Device;

import java.util.List;
import java.util.UUID;

/**
 * Service concerned with operating on devices
 */
public interface DeviceService {

    List<Device> getDevices();
    Integer getNumberOfDownedDevices();
    Device deleteDeviceFromUUID(UUID deviceId);

    void setArmed(ArmRequest armRequest);

}

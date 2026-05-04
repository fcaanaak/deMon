package com.modesec.server.services;

import com.modesec.server.models.ArmRequest;
import com.modesec.server.models.Device;

import java.util.List;

public interface DeviceService {

    List<Device> checkDevices();

    void setArmed(ArmRequest armRequest);

}

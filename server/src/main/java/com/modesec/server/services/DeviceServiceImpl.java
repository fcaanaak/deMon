package com.modesec.server.services;

import com.modesec.server.models.ArmRequest;
import com.modesec.server.models.Device;
import com.modesec.server.repositories.DeviceRepository;
import com.modesec.server.websocket.handler.DeviceMessagingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceMessagingHandler deviceMessagingHandler;

    private List<Device> devices;

    private void syncWithDB() {
        devices = deviceRepository.findAll();
    }

    private void updateDB() {
        deviceRepository.saveAll(devices);
    }

    private void notifyDevices(ArmRequest armRequest) throws IOException {
        deviceMessagingHandler.broadcastArmingToggle(armRequest);
    }

    /**
     * Retrieve all inactive devices
     * @return a list of all device that are currently inactive
     */
    @Override
    public List<Device> checkDevices() {
        return deviceRepository.findByIsOnline(Boolean.FALSE);
    }

    @Override
    public void setArmed(ArmRequest armRequest) {

        syncWithDB();
        devices.forEach( device -> device.setArmed(armRequest.isArmed()));
        updateDB();

        try {
            notifyDevices(armRequest);
        } catch (IOException e) {
            // Do nothing for now but should probably log when this happens or display something on client side
        }

    }

}

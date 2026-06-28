package com.modesec.server.services;

import com.modesec.server.models.ArmRequest;
import com.modesec.server.models.Device;
import com.modesec.server.repositories.DeviceRepository;
import com.modesec.server.websocket.handler.DeviceMessagingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    /**
     * Notify all connected devices of the incoming arming request
     *
     * @param armRequest the request to either arm or disarm all devices
     * @throws IOException
     */
    private void notifyDevices(ArmRequest armRequest) throws IOException {
        deviceMessagingHandler.broadcastArmingToggle(armRequest);
    }

    /**
     * Retrieve all devices
     * @return a list of all device that are currently inactive
     */
    @Override
    public List<Device> getDevices() {
        return deviceRepository.findAll();
    }

    /**
     * Get the number of offline devices
     * @return the number of offline devices
     */
    @Override
    public Integer getNumberOfDownedDevices() {return deviceRepository.findByIsOnline(Boolean.FALSE).size();}

    /**
     * Delete a device from the database given its ID
     * @param deviceId The UUID of the device (UUID4)
     * @return The deleted device, or null if no such device was found
     */
    @Override
    public Device deleteDeviceFromUUID(UUID deviceId) {
        Device deviceToDelete = deviceRepository.findById(deviceId).orElse(null);

        if (deviceToDelete != null) {
            deviceRepository.deleteById(deviceId);
        }

        return deviceToDelete;
    }

    /**
     * Arm all devices, whether they be online or offline
     *
     * @param armRequest the arming request to fulfill
     */
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

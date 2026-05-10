package com.modesec.server.services;

import com.modesec.server.models.Device;
import com.modesec.server.models.DeviceMessage;
import com.modesec.server.repositories.DeviceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class DeviceInitServiceImpl implements DeviceInitService{

    @Autowired
    DeviceRepository deviceRepository;

    private Device getDeviceFromDB(UUID uuid) {
        return deviceRepository.findById(uuid).orElse(null);
    }

    private Device getAndMarkDeviceAsOnline(UUID uuid) {

        Device foundDevice = getDeviceFromDB(uuid);

        if (foundDevice != null) {
            foundDevice.setOnline(Boolean.TRUE);
            deviceRepository.save(foundDevice);
        }

        return foundDevice;
    }

    private Device createNewDevice(DeviceMessage deviceMessage) {
        return new Device(
                deviceMessage.name(),
                UUID.fromString(deviceMessage.UUID()),
                Boolean.TRUE,
                Boolean.FALSE
        );
    }

    /**
     * Initialize a device
     *
     * @param deviceMessage the init message sent from a device
     * @return The device that is now marked online if its in the Database and a new device saved to the DB otherwise
     */
    @Override
    public Device deviceInit(DeviceMessage deviceMessage) {

        UUID deviceId = UUID.fromString(deviceMessage.UUID());

        Device searchedDevice = getAndMarkDeviceAsOnline(deviceId);

        if (searchedDevice != null) {
            return searchedDevice;
        }

        // Device not in DB
        Device newDevice = createNewDevice(deviceMessage);
        deviceRepository.save(newDevice);

        return newDevice;
    }
}

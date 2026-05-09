package com.modesec.server.services;

import com.modesec.server.models.Device;
import com.modesec.server.models.DeviceMessage;
import com.modesec.server.repositories.DeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class DeviceInitServiceImpl implements DeviceInitService{

    Logger logger = LoggerFactory.getLogger(DeviceInitServiceImpl.class);

    @Autowired
    DeviceRepository deviceRepository;

    private Boolean isDeviceInDB(UUID uuid) { // Use the device name for now, later will need to use uuid
        return deviceRepository.existsById(uuid);
    }

    private Optional<Device> getDeviceFromDB(UUID uuid) {// Also uses name, change later
        return deviceRepository.findById(uuid);
    }

    private Device getAndMarkDeviceAsOnline(UUID uuid) {

        Device foundDevice = getDeviceFromDB(uuid).orElse(null);

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

        if (isDeviceInDB(deviceId)) {
            return getAndMarkDeviceAsOnline(deviceId);
        }

        Device newDevice = createNewDevice(deviceMessage);
        deviceRepository.save(newDevice);

        return newDevice;
    }
}

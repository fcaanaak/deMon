package com.modesec.server.services;

import com.modesec.server.models.Device;
import com.modesec.server.models.DeviceMessage;
import com.modesec.server.repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeviceInitServiceImpl implements DeviceInitService{

    @Autowired
    DeviceRepository deviceRepository;

    private Boolean isDeviceInDB(String name) { // Use the device name for now, later will need to use UUID
        return deviceRepository.existsByName(name);
    }

    private Device getDeviceFromDB(String name) {// Also uses name, change later
        return deviceRepository.findByName(name);
    }

    private Device getAndMarkDeviceAsOnline(String name) {
        Device foundDevice = getDeviceFromDB(name);
        foundDevice.setOnline(Boolean.TRUE);
        deviceRepository.save(foundDevice);

        return foundDevice;
    }

    private Device createNewDevice(String name) {
        return new Device(
          name,
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

        String deviceName = deviceMessage.name();

        if (isDeviceInDB(deviceName)) {
            return getAndMarkDeviceAsOnline(deviceName);
        }

        Device newDevice = createNewDevice(deviceName);
        deviceRepository.save(newDevice);

        return newDevice;
    }
}

package com.modesec.server.services;

import com.modesec.server.models.Device;
import com.modesec.server.repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceHealthServiceImpl implements DeviceHealthService{

    @Autowired
    private DeviceRepository deviceRepository;

    private List<Integer> devices;


    @Override
    public List<Integer> checkDevices() {
        // Retrieve all inactive devices
        return List.of();
    }
}

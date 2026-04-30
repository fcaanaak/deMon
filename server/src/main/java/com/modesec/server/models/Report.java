package com.modesec.server.models;

import java.time.LocalDateTime;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String deviceName;

    public Report(String deviceName){
        this.deviceName = deviceName;
    }


//    private LocalDateTime detectionDateTime;

    public String getDeviceName() {
        return deviceName;
    }

//    public LocalDateTime getDetectionDateTime() {
//        return detectionDateTime;
//    }
}

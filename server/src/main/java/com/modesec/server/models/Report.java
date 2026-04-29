package com.modesec.server.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String deviceName;
    private LocalDateTime detectionDateTime;

    public String getDeviceName() {
        return deviceName;
    }

    public LocalDateTime getDetectionDateTime() {
        return detectionDateTime;
    }
}

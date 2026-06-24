package com.modesec.server.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

@Entity
public class Report implements Comparable<Report>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotNull
    private String deviceName;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Past
    @NotNull
    private LocalDateTime detectionDateTime;

    public Report(){}

    public Report(String deviceName, LocalDateTime detectionDateTime){
        this.detectionDateTime = detectionDateTime;
        this.deviceName = deviceName;
    }

    public String getDeviceName() {
        return deviceName;
    }
    public LocalDateTime getDetectionDateTime() {return detectionDateTime;}

    @Override
    public int compareTo(Report report) {
        return getDetectionDateTime().compareTo(report.getDetectionDateTime());
    }

}

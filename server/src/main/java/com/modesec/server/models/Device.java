package com.modesec.server.models;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

import java.util.UUID;

@Entity
public class Device {

    @Id
    private UUID id;// Primary key for DB

    private String name;

    private Boolean isOnline;

    private Boolean isArmed;

    public Device(){}

    public Device(String name, UUID id, Boolean isOnline, Boolean isArmed) {
        this.name = name;
        this.isOnline = isOnline;
        this.isArmed = isArmed;
        this.id = id;
    }

    public Boolean getArmed() {
        return isArmed;
    }

    public void setArmed(Boolean armed) {
        isArmed = armed;
    }

    public String getName() {
        return name;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public UUID getId() {
        return id;
    }
}

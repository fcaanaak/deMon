package com.modesec.server.models;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

import java.util.UUID;

@Entity
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;// Primary key for DB

    private String name;

    private Boolean isOnline;

    public Device(String name, Boolean isOnline) {
        this.name = name;
        this.isOnline = isOnline;
    }

}

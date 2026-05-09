package com.modesec.server.models;

import java.util.UUID;

public record DeviceMessage (String UUID, Boolean isInit, String name) {
}

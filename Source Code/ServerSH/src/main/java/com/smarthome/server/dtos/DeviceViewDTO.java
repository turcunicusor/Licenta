package com.smarthome.server.dtos;

import com.smarthome.server.entities.Device;

public class DeviceViewDTO {
    private String id;
    private String userEmail;
    private String ip;
    private int port;
    private String type;
    private String name;

    public DeviceViewDTO(String id, String userEmail, String ip, int port, String type, String name) {
        this.id = id;
        this.userEmail = userEmail;
        this.ip = ip;
        this.port = port;
        this.type = type;
        this.name = name;
    }

    public DeviceViewDTO() {
    }

    public DeviceViewDTO(Device device) {
        this.userEmail = device.getOwner().getEmail();
        this.ip = device.getIp().toString();
        this.port = device.getPort();
        this.type = device.getType();
        this.name = device.getName();
        this.id = device.getHash();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }
}

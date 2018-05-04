package com.smarthome.server.dtos;

import com.smarthome.server.entities.Device;
import org.hibernate.validator.constraints.NotEmpty;

public class DeviceDTO {
    @NotEmpty(message = "Please provide an Ip.")
    private String ip;
    private int port;
    @NotEmpty(message = "Please provide a Type.")
    private String type;
    @NotEmpty(message = "Please provide a Name.")
    private String name;

    public DeviceDTO(String ip, int port, String type, String name) {
        this.ip = ip;
        this.port = port;
        this.type = type;
        this.name = name;
    }

    public DeviceDTO() {
    }

    public DeviceDTO(Device device){
        this.ip = device.getIp().toString();
        this.port = device.getPort();
        this.type = device.getType();
        this.name = device.getName();
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
}

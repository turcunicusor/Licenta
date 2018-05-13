package com.smarthome.server.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.hibernate.validator.constraints.NotEmpty;

@JsonDeserialize
public class DeviceDTO {
    @NotEmpty(message = "Please provide an Ip.")
    private String ip;
    @NotEmpty(message = "Please provide a Name.")
    private String name;
    private Integer port;
    @NotEmpty(message = "Please provide a Type.")
    private String type;

    public DeviceDTO() {
    }

    public DeviceDTO(String ip, String name, int port, String type) {
        this.ip = ip;
        this.port = port;
        this.type = type;
        this.name = name;
    }
//
//
//
//    public DeviceDTO(Device device){
//        this.ip = device.getIp().toString();
//        this.port = device.getPort();
//        this.type = device.getType();
//        this.name = device.getName();
//    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
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

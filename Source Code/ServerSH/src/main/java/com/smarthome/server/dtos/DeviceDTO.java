package com.smarthome.server.dtos;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class DeviceDTO {
    @Email(message = "Please provide a valid Email.")
    @NotEmpty(message = "Please provide an Email.")
    private String userEmail;
    @NotEmpty(message = "Please provide an Ip.")
    private String ip;
//    @NotEmpty(message = "Please provide a Port.")
    private int port;
    @NotEmpty(message = "Please provide a Type.")
    private String type;

    public DeviceDTO(String userEmail, String ip, int port, String type) {
        this.userEmail = userEmail;
        this.ip = ip;
        this.port = port;
        this.type = type;
    }

    public DeviceDTO() {
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
}

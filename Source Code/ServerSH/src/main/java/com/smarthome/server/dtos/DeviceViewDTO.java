package com.smarthome.server.dtos;

import com.smarthome.server.entities.Device;

import java.util.HashMap;

public class DeviceViewDTO {
    private String id;
    private String ip;
    private int port;
    private String type;
    private String name;
    private HashMap<String, String> params;

    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    public DeviceViewDTO(String id, String ip, int port, String type, String name) {
        this.id = id;
        this.ip = ip;
        this.port = port;
        this.type = type;
        this.name = name;
        params = new HashMap<>();
        params.put("test", "test");
    }

    public DeviceViewDTO() {
        params = new HashMap<>();
        params.put("test", "test");
    }

    public DeviceViewDTO(Device device) {
        this.ip = device.getIp().toString();
        this.port = device.getPort();
        this.type = device.getType();
        this.name = device.getName();
        this.id = device.getHash();
        this.params = new HashMap<>();
        this.params.put("test", "test");
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

    public String getId() {
        return id;
    }
}

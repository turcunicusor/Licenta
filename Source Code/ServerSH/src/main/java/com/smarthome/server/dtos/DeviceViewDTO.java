package com.smarthome.server.dtos;

import com.smarthome.server.entities.Device;
import com.smarthome.server.hal.Generic.ParamDescription;

import java.util.HashMap;

public class DeviceViewDTO {
    private String id;
    private String ip;
    private int port;
    private String type;
    private String name;
    private HashMap<String, ParamDescription> acceptedParams;
    private HashMap<String, String> params;

    public DeviceViewDTO(String id, String ip, int port, String type, String name) {
        this.id = id;
        this.ip = ip;
        this.port = port;
        this.type = type;
        this.name = name;
        this.acceptedParams = new HashMap<>();
        this.acceptedParams.put("intensitate", new ParamDescription(false, "int"));
        this.acceptedParams.put("tensiune", new ParamDescription(true, "boolean"));
        this.params = new HashMap<>();
        this.params.put("intensitate", "0");
        this.params.put("tensiune", "1");
    }

    public DeviceViewDTO() {
        this.acceptedParams = new HashMap<>();
        this.acceptedParams.put("intensitate", new ParamDescription(false, "int"));
        this.acceptedParams.put("tensiune", new ParamDescription(true, "boolean"));
        this.params = new HashMap<>();
        this.params.put("intensitate", "0");
        this.params.put("tensiune", "1");
    }

    public DeviceViewDTO(Device device) {
        this.ip = device.getIp().toString();
        this.port = device.getPort();
        this.type = device.getType();
        this.name = device.getName();
        this.id = device.getHash();
        this.params = new HashMap<>();
        this.params.put("intensitate", "intensitate");
        this.params.put("tensiune", "1");
        this.acceptedParams = new HashMap<>();
        this.acceptedParams.put("intensitate", new ParamDescription(false, "int"));
        this.acceptedParams.put("tensiune", new ParamDescription(true, "boolean"));
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
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

    public HashMap<String, ParamDescription> getAcceptedParams() {
        return acceptedParams;
    }

    public void setAcceptedParams(HashMap<String, ParamDescription> acceptedParams) {
        this.acceptedParams = acceptedParams;
    }
}

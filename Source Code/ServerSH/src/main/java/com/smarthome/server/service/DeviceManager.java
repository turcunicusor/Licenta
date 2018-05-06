package com.smarthome.server.service;


import com.smarthome.server.entities.Device;
import com.smarthome.server.hal.Generic.DeviceStatus;
import com.smarthome.server.hal.Generic.IDevice;
import com.smarthome.server.hal.HalDevice;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class DeviceManager {
    private static final String SSL_KEY = "javax.net.ssl.trustStore";
    private static final String SSL_VALUE = "C:\\Program Files\\Java\\jdk1.8.0_144\\bin\\demo";
    private HashMap<String, IDevice> devices;

    public DeviceManager() {
        System.setProperty(SSL_KEY, SSL_VALUE);
        this.devices = new HashMap<>();
    }

    public void registerDevice(Device device) throws Exception {
        try {
            this.devices.put(device.getHash(), new HalDevice(device));
        } catch (Exception e) {
            throw new Exception(String.format("A device with ip '%s' and port '%s' already registered.", device.getIp().toString(), device.getPort()));
        }
    }

    public IDevice getHalDevice(String hash) {
        return devices.get(hash);
    }

    public Device getDevice(String hash) {
        return devices.get(hash).getDevice();
    }

    public void deleteHalDevice(String hash){
        IDevice device = this.devices.get(hash);
        try {
            if (device.getStatus() != DeviceStatus.OPENED)
                device.closeConnection();
        }
        catch (Exception e){
            System.err.println("deleteHalDevice");
            e.printStackTrace();
        }
        this.devices.remove(hash);
    }
}

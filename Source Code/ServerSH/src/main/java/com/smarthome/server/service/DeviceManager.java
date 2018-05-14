package com.smarthome.server.service;


import com.smarthome.server.entities.Device;
import com.smarthome.server.entities.User;
import com.smarthome.server.hal.Generic.IDevice;
import com.smarthome.server.hal.HalDevice;
import com.smarthome.server.repositories.DeviceRepository;
import com.smarthome.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class DeviceManager {
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;

    private HashMap<String, IDevice> halDevices;

    @Autowired
    public DeviceManager(UserRepository userRepository, DeviceRepository deviceRepository) {
        this.halDevices = new HashMap<>();
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
    }

    public void editDevice(Device device) throws IOException {
        halDevices.get(device.getHash()).closeConnection();
        halDevices.get(device.getHash()).setDevice(device);
        this.deviceRepository.save(device);
    }

    public IDevice getHalDevice(String hash) {
        return halDevices.get(hash);
    }

    public Device getDevice(String hash) {
        return halDevices.get(hash).getDevice();
    }

    public void registerDevice(Device device) {
        this.deviceRepository.save(device);
        this.halDevices.put(device.getHash(), new HalDevice(device));
    }

    public void deleteHalDevice(String hash) {
        IDevice device = this.halDevices.get(hash);
        try {
            device.closeConnection();
        } catch (Exception e) {
            System.err.println("deleteHalDevice");
            e.printStackTrace();
        }
        this.halDevices.remove(hash);
        this.deviceRepository.delete(device.getDevice());
    }

    public void userLogin(String userEmail) {
        System.out.println("DEVICE MANAGER USER LOGIN");
        User user = userRepository.findByEmail(userEmail);
        List<Device> devices = deviceRepository.findAllByOwner(user);
        // register devices
        for (Device device : devices)
            registerDevice(device);
    }

    public void userLogout(String userEmail) {
        System.out.println("DEVICE MANAGER USER LOGOUT");
        User user = userRepository.findByEmail(userEmail);
        List<Device> devices = deviceRepository.findAllByOwner(user);
        // release resources
        for (Device device : devices) {
            try {
                this.halDevices.get(device.getHash()).closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
                // to decide what to do here, exception list?
            }
        }
        for (Device device : devices)
            halDevices.remove(device.getHash());
    }
}
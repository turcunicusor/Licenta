package com.smarthome.server.controllers;

import com.smarthome.server.dtos.DeviceDTO;
import com.smarthome.server.entities.Device;
import com.smarthome.server.entities.User;
import com.smarthome.server.repositories.DeviceRepository;
import com.smarthome.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/devices")
public class DevicesController {
    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;

    @Autowired
    public DevicesController(DeviceRepository deviceRepository, UserRepository userRepository) {
        this.deviceRepository = deviceRepository;
        this.userRepository = userRepository;
    }

    @PostMapping()
    ResponseEntity add(@Valid @RequestBody DeviceDTO deviceDTO){
        User user = userRepository.findByEmail(deviceDTO.getUserEmail());
        if(user == null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No user found with that email.");
        Device device;
        try {
            device = new Device(InetAddress.getByName(deviceDTO.getIp()), deviceDTO.getPort(), deviceDTO.getType());
        } catch (UnknownHostException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("Ip '%s' is not a valid ip address.", deviceDTO.getIp()));
        }
        device.setOwner(user);
        deviceRepository.save(device);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}

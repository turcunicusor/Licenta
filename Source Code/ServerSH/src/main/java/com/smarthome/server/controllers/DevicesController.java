package com.smarthome.server.controllers;

import com.smarthome.server.dtos.DeviceDTO;
import com.smarthome.server.dtos.DeviceViewDTO;
import com.smarthome.server.dtos.ParamsDTO;
import com.smarthome.server.entities.Device;
import com.smarthome.server.entities.User;
import com.smarthome.server.hal.Generic.IDevice;
import com.smarthome.server.repositories.DeviceRepository;
import com.smarthome.server.repositories.UserRepository;
import com.smarthome.server.security.TokenAuthenticationService;
import com.smarthome.server.service.DeviceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/device")
public class DevicesController {
    private final DeviceRepository deviceRepository;
    private final DeviceManager deviceManager;
    private final UserRepository userRepository;

    @Autowired
    public DevicesController(DeviceRepository deviceRepository,
                             UserRepository userRepository,
                             DeviceManager deviceManager) {
        this.deviceRepository = deviceRepository;
        this.userRepository = userRepository;
        this.deviceManager = deviceManager;
    }

    @GetMapping()
    public ResponseEntity<?> findByHash(@RequestParam("device") String hash, @RequestHeader("Authorization") String token) {
        String ownerEmail = TokenAuthenticationService.decodeToken(token);
        IDevice device = deviceManager.getHalDevice(hash);
        if (device == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No device found with that hash.");
        if (!device.getDevice().getOwner().getEmail().equals(ownerEmail))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not enough privileges.");
        DeviceViewDTO deviceViewDTO = new DeviceViewDTO(device);
        return ResponseEntity.status(HttpStatus.OK).body(deviceViewDTO);
    }

    @GetMapping("/all")
    public List<DeviceViewDTO> getAll(@RequestHeader("Authorization") String token) {
        String userEmail = TokenAuthenticationService.decodeToken(token);
        List<DeviceViewDTO> devices = new ArrayList<>();
        for (Device device : deviceRepository.findAllByOwner(userRepository.findByEmail(userEmail)))
            devices.add(new DeviceViewDTO(deviceManager.getHalDevice(device.getHash())));
        return devices;
    }

    @PostMapping()
    ResponseEntity add(@RequestHeader("Authorization") String token, @RequestBody DeviceDTO deviceDTO) {
        String userEmail = TokenAuthenticationService.decodeToken(token);
        User user = userRepository.findByEmail(userEmail);
        if (user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user found with that email.");
        Device device;
        try {
            device = new Device(InetAddress.getByName(deviceDTO.getIp()), deviceDTO.getPort(), deviceDTO.getType(), deviceDTO.getName());
        } catch (UnknownHostException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("Ip '%s' is not a valid ip address.", deviceDTO.getIp()));
        }
        device.setOwner(user);
        deviceManager.registerDevice(device);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @DeleteMapping()
    ResponseEntity delete(@RequestHeader("Authorization") String token, @RequestParam("device") String hash) {
        ResponseEntity response =  checkDevice(token, hash);
        if (response.getStatusCode() != HttpStatus.OK) return response;
        deviceManager.deleteHalDevice(hash);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @PutMapping("/testConnection")
    ResponseEntity testConnection(@RequestHeader("Authorization") String token, @RequestParam("device") String hash) {
        ResponseEntity response =  checkDevice(token, hash);
        if (response.getStatusCode() != HttpStatus.OK) return response;
        try {
            deviceManager.getHalDevice(hash).testConnection();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Test connection failed. Reason: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @PutMapping("/connect")
    ResponseEntity connect(@RequestHeader("Authorization") String token, @RequestParam("device") String hash) {
        ResponseEntity response =  checkDevice(token, hash);
        if (response.getStatusCode() != HttpStatus.OK) return response;
        try {
            deviceManager.getHalDevice(hash).connect();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Connect failed. Reason: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @PutMapping("/open")
    ResponseEntity open(@RequestHeader("Authorization") String token, @RequestParam("device") String hash) {
        ResponseEntity response =  checkDevice(token, hash);
        if (response.getStatusCode() != HttpStatus.OK) return response;
        try {
            deviceManager.getHalDevice(hash).open();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Open failed. Reason: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @PostMapping("/setParams")
    ResponseEntity setParams(@RequestHeader("Authorization") String token, @RequestParam("device") String hash, @RequestBody ParamsDTO paramsDTO) {
        ResponseEntity response =  checkDevice(token, hash);
        if (response.getStatusCode() != HttpStatus.OK) return response;
        try {
            System.out.println("ParamsDTO"+paramsDTO.getParams());
            deviceManager.getHalDevice(hash).command(paramsDTO.getParams());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Set parameter failed. Reason: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @PutMapping("/close")
    ResponseEntity close(@RequestHeader("Authorization") String token, @RequestParam("device") String hash) {
        ResponseEntity response =  checkDevice(token, hash);
        if (response.getStatusCode() != HttpStatus.OK) return response;
        try {
            deviceManager.getHalDevice(hash).close();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Close failed. Reason: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @PutMapping("/disconnect")
    ResponseEntity disconnect(@RequestHeader("Authorization") String token, @RequestParam("device") String hash) {
        ResponseEntity response =  checkDevice(token, hash);
        if (response.getStatusCode() != HttpStatus.OK) return response;
        try {
            deviceManager.getHalDevice(hash).closeConnection();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Disconnect failed. Reason: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @PutMapping()
    ResponseEntity edit(@RequestHeader("Authorization") String token, @RequestParam("device") String hash, @RequestBody DeviceDTO deviceDTO) {
        String ownerEmail = TokenAuthenticationService.decodeToken(token);
        Device device = deviceManager.getDevice(hash);
        if (device == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No device found with that hash.");
        if (!device.getOwner().getEmail().equals(ownerEmail))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not enough privileges.");
        try {
            if (!deviceDTO.getIp().isEmpty()) device.setIp(InetAddress.getByName(deviceDTO.getIp()));
            if (deviceDTO.getPort() != 0) device.setPort(deviceDTO.getPort());
            if (!deviceDTO.getType().isEmpty()) device.setType(deviceDTO.getType());
            if (!deviceDTO.getName().isEmpty()) device.setName(deviceDTO.getName());
        } catch (UnknownHostException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("Ip '%s' is not a valid ip address.", deviceDTO.getIp()));
        }
        try {
            deviceManager.editDevice(device);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to edit device. Reason: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    private ResponseEntity checkDevice(String token, String hash) {
        String ownerEmail = TokenAuthenticationService.decodeToken(token);
        Device device = deviceManager.getDevice(hash);
        if (device == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No device found with that hash.");
        if (!device.getOwner().getEmail().equals(ownerEmail))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not enough privileges.");
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}

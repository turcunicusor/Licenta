package com.smarthome.server.controllers;

import com.smarthome.server.dtos.DeviceDTO;
import com.smarthome.server.dtos.DeviceViewDTO;
import com.smarthome.server.entities.Device;
import com.smarthome.server.entities.User;
import com.smarthome.server.repositories.DeviceRepository;
import com.smarthome.server.repositories.UserRepository;
import com.smarthome.server.security.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/device")
public class DevicesController {
    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;

    @Autowired
    public DevicesController(DeviceRepository deviceRepository, UserRepository userRepository) {
        this.deviceRepository = deviceRepository;
        this.userRepository = userRepository;
    }

    @GetMapping()
    public ResponseEntity<?> findByHash(@RequestParam("device") String hash, @RequestHeader("Authorization") String token) {
        String ownerEmail = TokenAuthenticationService.decodeToken(token);
        Device device = deviceRepository.findByHash(hash);
        if (device == null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No device found with that hash.");
        if (!device.getOwner().getEmail().equals(ownerEmail))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not enough privileges.");
        DeviceViewDTO deviceViewDTO = new DeviceViewDTO(device);
        return ResponseEntity.status(HttpStatus.OK).body(deviceViewDTO);
    }

    @GetMapping("/all")
    public List<DeviceViewDTO> getAllByEmail(@RequestHeader("Authorization") String token) {
        String userEmail = TokenAuthenticationService.decodeToken(token);
        List<DeviceViewDTO> devices = new ArrayList<>();
        for (Device device : deviceRepository.findAllByOwner(userRepository.findByEmail(userEmail)))
            devices.add(new DeviceViewDTO(device));
        return devices;
    }

    @PostMapping()
    ResponseEntity add(@RequestHeader("Authorization") String token, @Valid @RequestBody DeviceDTO deviceDTO) {
        String userEmail = TokenAuthenticationService.decodeToken(token);
        User user = userRepository.findByEmail(userEmail);
        if (user == null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No user found with that email.");
        Device device;
        try {
            device = new Device(InetAddress.getByName(deviceDTO.getIp()), deviceDTO.getPort(), deviceDTO.getType(), deviceDTO.getName());
        } catch (UnknownHostException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("Ip '%s' is not a valid ip address.", deviceDTO.getIp()));
        } catch (NoSuchAlgorithmException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        device.setOwner(user);
        deviceRepository.save(device);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @DeleteMapping
    ResponseEntity delete(@RequestHeader("Authorization") String token, @RequestParam("device") String hash) {
        String ownerEmail = TokenAuthenticationService.decodeToken(token);
        Device device = deviceRepository.findByHash(hash);
        if (device == null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No device found with that hash.");
        if (!device.getOwner().getEmail().equals(ownerEmail))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not enough privileges.");
        // here should check close connection with that device
        // and removed from device manager
        deviceRepository.delete(device);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @PutMapping()
    ResponseEntity edit(@RequestHeader("Authorization") String token, @RequestParam("device") String hash, @RequestBody DeviceDTO deviceDTO) {
        String ownerEmail = TokenAuthenticationService.decodeToken(token);
        Device device = deviceRepository.findByHash(hash);
        if (device == null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No device found with that hash.");
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
        deviceRepository.save(device);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}

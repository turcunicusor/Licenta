package com.smarthome.server.controllers;

import com.smarthome.server.dtos.ProfileDTO;
import com.smarthome.server.entities.User;
import com.smarthome.server.repositories.UserRepository;
import com.smarthome.server.security.TokenAuthenticationService;
import com.smarthome.server.service.DeviceManager;
import com.smarthome.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final DeviceManager deviceManager;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService,
                          BCryptPasswordEncoder passwordEncoder, DeviceManager deviceManager) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.deviceManager = deviceManager;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity logout(@RequestHeader("Authorization") String token) {
        try {
            String userEmail = TokenAuthenticationService.decodeToken(token);
            userService.loggedOut(userEmail);
            deviceManager.userLogout(userEmail);
            return ResponseEntity.status(HttpStatus.OK).body("");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(value = "/profile")
    ResponseEntity profile(@RequestHeader("Authorization") String token) {
        String userEmail = TokenAuthenticationService.decodeToken(token);
        User user = userService.findUserByEmail(userEmail);
        if (user == null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No user found with that email.");
        return ResponseEntity.status(HttpStatus.OK).body(user.toUserDTO());
    }

    @PostMapping(value = "/profile")
    ResponseEntity changeProfile(@RequestHeader("Authorization") String token, @Valid @RequestBody ProfileDTO profile) {
        String userEmail = TokenAuthenticationService.decodeToken(token);
        System.out.println("useremail" + userEmail);
        System.out.println("profileemail" + profile.getOldEmail());
        if (!userEmail.equals(profile.getOldEmail()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
        User user = userService.findUserByEmail(profile.getOldEmail());
        if (user == null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No user found with that email.");
        if (!profile.getNewPassword().isEmpty() || !profile.getOldPassword().isEmpty()) {
            if (!passwordEncoder.matches(profile.getOldPassword(), user.getPassword()))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid old password.");
            else {
                user.setPassword(passwordEncoder.encode(profile.getNewPassword()));
            }
        }
        user.setFirstName(profile.getFirstName());
        user.setLastName(profile.getLastName());
        user.setEmail(profile.getNewEmail());
        userService.update(user);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}

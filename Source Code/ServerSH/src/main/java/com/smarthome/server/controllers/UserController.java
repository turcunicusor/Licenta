package com.smarthome.server.controllers;

import com.smarthome.server.dtos.*;
import com.smarthome.server.entities.User;
import com.smarthome.server.repositories.UserRepository;
import com.smarthome.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity logout(@RequestBody LogoutDTO logout) {
        try {
            userService.loggedOut(logout.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body("");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(value = "/profile")
    ResponseEntity profile(@RequestParam("email") String email) {
        User user = userService.findUserByEmail(email);
        if (user == null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No user found with that email.");
        return ResponseEntity.status(HttpStatus.OK).body(user.toUserDTO());
    }

    @PostMapping(value = "/profile")
    ResponseEntity changeProfile(@Valid @RequestBody ProfileDTO profile) {
        User user = userService.findUserByEmail(profile.getOldEmail());
        if (user == null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No user found with that email.");
        if (!profile.getNewPassword().isEmpty()|| !profile.getOldPassword().isEmpty()) {
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

    @GetMapping("/all")
    Iterable<UserDTO> getAll() {
        List<User> users = userRepository.findAll();
        List<UserDTO> usersDto = new ArrayList<>();
        for (User user : users)
            usersDto.add(user.toUserDTO());
        return usersDto;
    }

    @PostMapping("/")
    User getByEmail(@Valid @RequestBody EmailDTO email) {
        return userRepository.findByEmail(email.getEmail());
    }
}

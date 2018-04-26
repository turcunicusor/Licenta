package com.smarthouse.server.controllers;

import com.smarthouse.server.dtos.RegisterDTO;
import com.smarthouse.server.entities.User;
import com.smarthouse.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class AuthenticationController {
    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity register(@Valid @RequestBody RegisterDTO registerDTO) {
        if (userService.findUserByEmail(registerDTO.getEmail()) != null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already taken.");
        userService.saveRegistered(new User(registerDTO.getFirstName(), registerDTO.getLastName(),
                registerDTO.getEmail(), registerDTO.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
}

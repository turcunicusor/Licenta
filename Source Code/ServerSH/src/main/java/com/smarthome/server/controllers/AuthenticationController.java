package com.smarthome.server.controllers;

import com.smarthome.server.dtos.LoginDTO;
import com.smarthome.server.dtos.RegisterDTO;
import com.smarthome.server.entities.User;
import com.smarthome.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

    @PostMapping("/login")
    void login(@RequestBody LoginDTO login) {
        // security package will take the request
    }

    @RequestMapping("/")
    String index(){
        return "index";
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity register(@Valid @RequestBody RegisterDTO registerDTO) {
        if (userService.findUserByEmail(registerDTO.getEmail()) != null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already taken.");
        userService.saveUser(new User(registerDTO.getFirstName(), registerDTO.getLastName(),
                registerDTO.getEmail(), registerDTO.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
}

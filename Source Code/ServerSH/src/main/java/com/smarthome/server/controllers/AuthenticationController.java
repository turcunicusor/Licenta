package com.smarthome.server.controllers;

import com.smarthome.server.dtos.LoginDTO;
import com.smarthome.server.dtos.LogoutDTO;
import com.smarthome.server.dtos.RegisterDTO;
import com.smarthome.server.entities.User;
import com.smarthome.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;

@RestController
@RequestMapping("/")
@CrossOrigin
public class AuthenticationController {
    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin
    @PostMapping("/login")
    void login(@RequestBody LoginDTO login) {
        // security package will take the request
    }

    @CrossOrigin
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity logout(@RequestBody LogoutDTO logout) {
        try {
            userService.loggedOut(logout.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body("");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @CrossOrigin
    @RequestMapping("/")
    String index(){
        return "index";
    }

    @CrossOrigin
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

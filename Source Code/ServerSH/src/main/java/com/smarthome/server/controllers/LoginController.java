package com.smarthome.server.controllers;

import com.smarthome.server.dtos.LoginDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class LoginController {
    @PostMapping("/login")
    void login(@RequestBody LoginDTO login) {
        // security package will take the request
    }
}

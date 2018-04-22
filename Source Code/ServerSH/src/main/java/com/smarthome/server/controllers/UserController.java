package com.smarthome.server.controllers;

import com.smarthome.server.dtos.EmailDTO;
import com.smarthome.server.entities.User;
import com.smarthome.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    private final UserRepository userRepository;
    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    Iterable<User> getAll() {
        return userRepository.findAll();
    }
    @PostMapping("/")
    User getByEmail(@Valid @RequestBody EmailDTO email) {
        return userRepository.findByEmail(email.getEmail());
    }
}

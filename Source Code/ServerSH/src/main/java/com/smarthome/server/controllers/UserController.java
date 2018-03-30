package com.smarthome.server.controllers;

import com.smarthome.server.dtos.RegisterDTO;
import com.smarthome.server.entities.User;
import com.smarthome.server.repositories.UserRepository;
import com.smarthome.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }


    @GetMapping("/add")
    void addUser() {
        userService.saveUser(new User("nicusor", "nicusor", "1", "1", "turcunick@gmail.com", 1, 1));
    }

    @GetMapping("/all")
    Iterable<User> getAll() {
        return userRepository.findAll();
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity register(@Valid @RequestBody RegisterDTO registerDTO) {
        if (userService.findUserByEmail(registerDTO.getEmail()) != null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already taken.");
        userService.saveUser(
                new User(registerDTO.getFirstName(),
                        registerDTO.getLastName(),
                        registerDTO.getUsername(),
                        registerDTO.getPassword(),
                        registerDTO.getEmail(), 0, 0));
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

//    @PostMapping("/login")
//    @ResponseStatus(HttpStatus.CREATED)
//    ResponseEntity login(@RequestBody LoginDTO login) {
//        if(PasswordEncoder.matches(login.getPassword(),userRepository.findByUsername(login.getUsername()).get(0).getPassword()))
//            return ResponseEntity.ok("Login succes.");
//        else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
//    }
}

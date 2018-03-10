package com.smarthome.server.controllers;

import com.smarthome.server.dtos.LoginDTO;
import com.smarthome.server.dtos.RegisterDTO;
import com.smarthome.server.entities.User;
import com.smarthome.server.repositories.UserRepository;
import com.smarthome.server.utils.PasswordEncoder;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/add")
    void addUser() {
        userRepository.save(new User("nicusor", "nicusor", "1", "1", 1));
    }

    @GetMapping("/all")
    Iterable<User> getAll() {
        return userRepository.findAll();
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity register(@RequestBody RegisterDTO registerDTO) {
        if(userRepository.findByUsername(registerDTO.getUsername()).size() > 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already taken.");
        userRepository.save(
                new User(registerDTO.getFirstName(),
                        registerDTO.getLastName(),
                        registerDTO.getUsername(),
                        PasswordEncoder.encode(registerDTO.getPassword()),
                        0)
        );
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity login(@RequestBody LoginDTO login) {
        if(PasswordEncoder.matches(login.getPassword(),userRepository.findByUsername(login.getUsername()).get(0).getPassword()))
            return ResponseEntity.ok("Login succes.");
        else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
    }
}

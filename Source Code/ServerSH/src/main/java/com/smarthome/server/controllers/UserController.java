package com.smarthome.server.controllers;

import com.smarthome.server.entities.User;
import com.smarthome.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    void addUser(){
        userRepository.save(new User("nicusor","nicusor", "1","1",1));
    }

    @RequestMapping(value ="/all", method = RequestMethod.GET)
    Iterable<User> getAll(){
        return userRepository.findAll();
    }
}

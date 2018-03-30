package com.smarthome.server.controllers;

import com.smarthome.server.entities.Role;
import com.smarthome.server.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RolesController {
    private final RoleRepository roleRepository;

    @Autowired
    public RolesController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostMapping("/add")
    ResponseEntity addRoles() {
        Role roleAdmin = new Role("ADMIN");
        Role roleUser = new Role("USER");
        roleRepository.save(roleAdmin);
        roleRepository.save(roleUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("Roles 'ADMIN', 'USER' created.");
    }
}

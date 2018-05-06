package com.smarthome.server.service;

import com.smarthome.server.entities.Role;
import com.smarthome.server.entities.User;
import com.smarthome.server.repositories.RoleRepository;
import com.smarthome.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveRegistered(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setStatus(UserStatus.Registered);
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        userRepository.save(user);
    }

    @Override
    public void loggedIn(String email) {
        User user = userRepository.findByEmail(email);
        user.setStatus(UserStatus.LoggedIn);
        userRepository.save(user);
    }

    @Override
    public void loggedOut(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null)
            throw new Exception("User does not exists in database.");
        if (user.getStatus() != UserStatus.LoggedIn.ordinal())
            throw new Exception("User is not logged in. Cannot logged out.");
        user.setStatus(UserStatus.LoggedOut);
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }
}

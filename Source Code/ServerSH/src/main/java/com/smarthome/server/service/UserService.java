package com.smarthome.server.service;

import com.smarthome.server.entities.User;

public interface UserService {
    User findUserByEmail(String email);
    void saveUser(User user);
}

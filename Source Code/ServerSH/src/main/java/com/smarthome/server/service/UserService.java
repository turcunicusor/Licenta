package com.smarthome.server.service;

import com.smarthome.server.entities.User;

public interface UserService {
    User findUserByEmail(String email);
    void saveRegistered(User user);
    void loggedIn(String email);
    void loggedOut(String email) throws Exception;
    void update(User user);
}

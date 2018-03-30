package com.smarthome.server.service;

import com.smarthome.server.entities.User;
import org.springframework.data.domain.Sort;

public interface UserService {
    User findUserByEmail(String email);
    void saveUser(User user);
    Iterable<User> findAll(Sort sort);
}

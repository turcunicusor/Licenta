package com.smarthome.server.repositories;

import com.smarthome.server.entities.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByFirstName(String name);
    User findByEmail(String email);
    List<User> findAll(Sort sort);
}
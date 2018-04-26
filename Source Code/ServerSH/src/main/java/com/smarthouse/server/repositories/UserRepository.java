package com.smarthouse.server.repositories;

import com.smarthouse.server.entities.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByFirstName(String name);
    User findByEmail(String email);
    List<User> findAll(Sort sort);
}
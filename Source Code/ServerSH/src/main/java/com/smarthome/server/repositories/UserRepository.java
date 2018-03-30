package com.smarthome.server.repositories;

import com.smarthome.server.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByFirstName(String name);
    User findByEmail(String email);
    List<User> findByUsername(String username);
    Iterable<User> findAll(Sort sort);
}
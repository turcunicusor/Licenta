package com.smarthome.server.repositories;

import com.smarthome.server.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByFirstName(String name);

    User findByAge(int age);

    User save(User persistent);

    Iterable<User> findAll(Sort sort);
}
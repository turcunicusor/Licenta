package com.smarthome.server.repositories;

import com.smarthome.server.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID>{
    Role findByRole(String role);
}

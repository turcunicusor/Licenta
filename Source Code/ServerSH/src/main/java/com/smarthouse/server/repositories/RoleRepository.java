package com.smarthouse.server.repositories;

import com.smarthouse.server.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID>{
    Role findByRole(String role);
}

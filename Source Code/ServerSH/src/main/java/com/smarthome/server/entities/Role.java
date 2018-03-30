package com.smarthome.server.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "role")
public class Role {
    //region private fields
    @Id
    @Column(name="role_id")
    private UUID id;
    private String role;
    //endregion

    //region contructors
    public Role() {
        this.id = UUID.randomUUID();
    }

    public Role(String role) {
        this.id = UUID.randomUUID();
        this.role = role;
    }
    //endregion

    //region setters and getters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    //endregion
}

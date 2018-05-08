package com.smarthome.server.entities;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
@Transactional
public class Role {
    //region private fields
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role;
    @ManyToMany(mappedBy = "roles",cascade =
            {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<User> users;
    //endregion

    //region contructors
    public Role() {
        this.users = new HashSet<>();
    }

    public Role(String role) {
        this.role = role;
        this.users = new HashSet<>();
    }
    //endregion

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    //endregion
}

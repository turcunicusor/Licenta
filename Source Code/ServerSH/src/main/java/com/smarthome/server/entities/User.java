package com.smarthome.server.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "user")
public class User {
    //region private fields
    @Id
    @Column(name = "user_id")
    private UUID id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private int age;
    private int active;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    //endregion

    //region constructors
    public User() {
        this.id = UUID.randomUUID();
        this.roles = new HashSet<>();
    }
    public User(String firstName, String lastName, String username, String password, String email, int age, int active) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.age = age;
        this.id = UUID.randomUUID();
        this.active = active;
        this.roles = new HashSet<>();
    }
    //endregion

    //region setters and getters
    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles.addAll(roles);
    }
    //endregion

    //region override methods
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User{");
        sb.append("id=").append(this.id);
        sb.append(", username=").append(this.username);
        sb.append(", firstName=").append(this.firstName);
        sb.append(", lastName=").append(this.lastName);
        sb.append(", age=").append(this.age);
        sb.append(", password=").append(this.password);
        return sb.toString();
    }
    //endregion
}

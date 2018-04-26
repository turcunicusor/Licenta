package com.smarthouse.server.entities;

import com.smarthouse.server.dtos.UserDTO;
import com.smarthouse.server.service.Status;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    //region private fields
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private int status;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    //endregion

    //region constructors
    public User() {
        this.roles = new HashSet<>();
        this.status = Status.Created.ordinal();
    }
    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.status = Status.Created.ordinal();
        this.roles = new HashSet<>();
    }
    //endregion

    //region setters and getters
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

    public int getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status.ordinal();
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
        sb.append(", firstName=").append(this.firstName);
        sb.append(", lastName=").append(this.lastName);
        sb.append(", email=").append(this.email);
        sb.append(", password=").append(this.password);
        return sb.toString();
    }
    //endregion

    public UserDTO toUserDTO(){
        return new UserDTO(firstName,lastName,email);
    }
}

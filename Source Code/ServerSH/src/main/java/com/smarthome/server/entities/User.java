package com.smarthome.server.entities;

import com.smarthome.server.dtos.UserDTO;
import com.smarthome.server.service.UserStatus;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@Transactional
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
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    @OneToMany(mappedBy = "owner")
    private List<Device> devices;
    //endregion

    //region constructors
    public User() {
        this.roles = new HashSet<>();
        this.status = UserStatus.Created.ordinal();
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.status = UserStatus.Created.ordinal();
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

    public void setStatus(UserStatus status) {
        this.status = status.ordinal();
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles.addAll(roles);
    }

    public List<Device> getDevices() {
        return devices;
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

    public UserDTO toUserDTO() {
        return new UserDTO(firstName, lastName, email);
    }
}

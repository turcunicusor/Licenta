package com.smarthouse.server.dtos;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class RegisterDTO {
    @NotEmpty(message = "Please provide your first name.")
    private String firstName;
    @NotEmpty(message = "Please provide your last name.")
    private String lastName;
    @Email(message = "Please provide a valid Email.")
    @NotEmpty(message = "Please provide an Email.")
    private String email;
    @Length(min = 7, message = "Your password must have at least 7 characters.")
    @NotEmpty(message = "Please provide your password.")
    private String password;

    public RegisterDTO() {
    }

    public RegisterDTO(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

package com.smarthouse.server.dtos;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class ProfileDTO {
    @NotEmpty(message = "Please provide your first name.")
    private String firstName;
    @NotEmpty(message = "Please provide your last name.")
    private String lastName;
    @Email(message = "Please provide a valid Email.")
    @NotEmpty(message = "Please provide an Email.")
    private String newEmail;
    private String oldEmail;
    private String oldPassword;
    private String newPassword;

    ProfileDTO(){
    }

    public ProfileDTO(String firstName, String lastName, String email, String oldEmail, String oldPassword, String newPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.newEmail = email;
        this.oldEmail = oldEmail;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public String getOldEmail() {
        return oldEmail;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}

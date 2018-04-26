package com.smarthouse.server.dtos;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class EmailDTO {
    @Email(message = "Please provide a valid Email.")
    @NotEmpty(message = "Please provide an Email.")
    private String email;

    public EmailDTO(String email) {
        this.email = email;
    }

    public EmailDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

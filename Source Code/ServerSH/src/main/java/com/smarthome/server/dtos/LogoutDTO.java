package com.smarthome.server.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

//@JsonDeserialize(as = LogoutDTO.class)
public class LogoutDTO {
    @Email(message = "Please provide a valid Email.")
    @NotEmpty(message = "Please provide an Email.")
    private String email;

    public LogoutDTO() {
    }

    public LogoutDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}

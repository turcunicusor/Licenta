package com.smarthome.server.dtos;

public class RegisterDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    public RegisterDTO(){
    }

    public RegisterDTO(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

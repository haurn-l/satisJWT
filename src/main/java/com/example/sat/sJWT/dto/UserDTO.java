package com.example.sat.sJWT.dto;

import jakarta.validation.constraints.*;

public class UserDTO {
    @NotBlank(message = "İsim zorunlu")
    private String name;

    @Email(message = "Geçerli bir email giriniz")
    @NotBlank(message = "Email zorunlu")
    private String email;

    @NotBlank(message = "Şifre zorunlu")
    @Size(min = 6, message = "Şifre en az 6 karakter olmalı")
    private String password;

    public UserDTO() {}

    public UserDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
} 
package com.example.sat.sJWT.dto;

import java.math.BigDecimal;

public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String role;
    private BigDecimal balance;

    public UserResponseDTO() {}

    public UserResponseDTO(Long id, String name, String email, String role, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.balance = balance;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
} 
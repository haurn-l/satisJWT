package com.example.sat.sJWT.service;

import com.example.sat.sJWT.dto.UserDTO;
import com.example.sat.sJWT.dto.UserResponseDTO;

public interface UserService {
    UserResponseDTO register(UserDTO dto);
    UserResponseDTO getProfile(String email);
    UserResponseDTO addBalance(String email, java.math.BigDecimal amount);
} 
package com.example.sat.sJWT.controller;

import com.example.sat.sJWT.dto.UserDTO;
import com.example.sat.sJWT.dto.UserResponseDTO;
import com.example.sat.sJWT.service.UserService;
import com.example.sat.sJWT.util.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityUtils securityUtils;

    // Kayıt (register) - Herkese açık
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserDTO dto) {
        return ResponseEntity.ok(userService.register(dto));
    }

    // Profil görüntüleme - Token'dan email alınır
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getProfile() {
        String currentUserEmail = securityUtils.getCurrentUserEmail();
        return ResponseEntity.ok(userService.getProfile(currentUserEmail));
    }

    // Bakiye yükleme - Token'dan email alınır
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/addBalance")
    public ResponseEntity<UserResponseDTO> addBalance(@RequestParam BigDecimal amount) {
        String currentUserEmail = securityUtils.getCurrentUserEmail();
        return ResponseEntity.ok(userService.addBalance(currentUserEmail, amount));
    }
}
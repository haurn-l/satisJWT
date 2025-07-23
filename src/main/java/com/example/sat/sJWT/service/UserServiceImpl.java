package com.example.sat.sJWT.service;

import com.example.sat.sJWT.dto.UserDTO;
import com.example.sat.sJWT.dto.UserResponseDTO;
import com.example.sat.sJWT.entity.User;
import com.example.sat.sJWT.exception.ResourceNotFoundException;
import com.example.sat.sJWT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponseDTO register(UserDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Bu email ile kayıtlı kullanıcı zaten var.");
        }
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // Şifre encode edildi
        user.setRole("ROLE_USER");
        user.setBalance(BigDecimal.ZERO);
        User saved = userRepository.save(user);
        return toResponseDTO(saved);
    }

    @Override
    public UserResponseDTO getProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Kullanıcı bulunamadı: " + email));
        return toResponseDTO(user);
    }

    @Override
    @Transactional
    public UserResponseDTO addBalance(String email, BigDecimal amount) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Kullanıcı bulunamadı: " + email));
        user.setBalance(user.getBalance().add(amount));
        User updated = userRepository.save(user);
        return toResponseDTO(updated);
    }

    private UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getBalance()
        );
    }
} 
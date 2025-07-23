package com.example.sat.sJWT.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    /**
     * Şu anda authenticate olmuş kullanıcının email'ini alır
     * @return kullanıcı email'i veya null
     */
    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername(); // Email'i döner
        }
        return null;
    }

    /**
     * Şu anda authenticate olmuş kullanıcının role'ünü alır
     * @return kullanıcı role'ü veya null
     */
    public String getCurrentUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            return authentication.getAuthorities().iterator().next().getAuthority();
        }
        return null;
    }

    /**
     * Kullanıcının belirli bir role'ü olup olmadığını kontrol eder
     * @param role kontrol edilecek role
     * @return true/false
     */
    public boolean hasRole(String role) {
        String currentRole = getCurrentUserRole();
        return currentRole != null && currentRole.equals(role);
    }

    /**
     * Kullanıcının admin olup olmadığını kontrol eder
     * @return true/false
     */
    public boolean isAdmin() {
        return hasRole("ROLE_ADMIN");
    }

    /**
     * Kullanıcının user olup olmadığını kontrol eder
     * @return true/false
     */
    public boolean isUser() {
        return hasRole("ROLE_USER");
    }
}
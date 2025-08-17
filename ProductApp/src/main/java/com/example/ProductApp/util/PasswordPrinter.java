package com.example.ProductApp.util;

import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordPrinter {

    private final PasswordEncoder passwordEncoder;

    public PasswordPrinter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void printEncodedPassword() {
        String rawPassword = "password123";
        String encoded = passwordEncoder.encode(rawPassword);
        System.out.println("Encoded password: " + encoded);
    }
}


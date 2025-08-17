package com.example.ProductApp.security;

import com.example.ProductApp.model.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

public class JwtServiceTest {

    private JwtService jwtService;

    private final String secret = "ThisIsASecretKeyForJWT1234567890ABCDEF";
    private final long expiration = 1000 * 60 * 60; // 1 hour

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        jwtService.secretKeyString = secret;
        jwtService.expirationMillis = expiration;
        jwtService.init();
    }

    private User getTestUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("john");
        user.setRole("ADMIN");
        return user;
    }

    @Test
    void testGenerateAndExtractUsername() {
        User user = getTestUser();
        String token = jwtService.generateToken(user);
        String username = jwtService.extractUsername(token);
        assertEquals("john", username);
    }

    @Test
    void testValidateToken_Success() {
        User user = getTestUser();
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("john");

        String token = jwtService.generateToken(user);
        assertTrue(jwtService.validateToken(token, userDetails));
    }

    @Test
    void testValidateToken_WrongUsername() {
        User user = getTestUser();
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("wronguser");

        String token = jwtService.generateToken(user);
        assertFalse(jwtService.validateToken(token, userDetails));
    }

    @Test
    void testValidateToken_TokenExpired() throws InterruptedException {
        JwtService shortExpiryService = new JwtService();
        shortExpiryService.secretKeyString = secret;
        shortExpiryService.expirationMillis = 50; // very short expiry
        shortExpiryService.init();

        User user = getTestUser();
        String token = shortExpiryService.generateToken(user);

        Thread.sleep(100); // wait for token to expire

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("john");

        assertFalse(shortExpiryService.validateToken(token, userDetails));
    }

    @Test
    void testValidateToken_InvalidToken() {
        String invalidToken = "not.a.valid.token";
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("john");

        assertThrows(Exception.class, () -> {
            jwtService.validateToken(invalidToken, userDetails);
        });
    }

    @Test
    void testExtractUsername_InvalidToken() {
        String invalidToken = "abc.def.ghi";
        assertThrows(Exception.class, () -> {
            jwtService.extractUsername(invalidToken);
        });
    }

    @Test
    void testTamperedToken_ShouldFailValidation() {
        User user = getTestUser();
        String token = jwtService.generateToken(user);

        // Tamper the token slightly
        String tamperedToken = token.substring(0, token.length() - 1) + "x";

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("john");

        assertThrows(Exception.class, () -> {
            jwtService.validateToken(tamperedToken, userDetails);
        });
    }
}


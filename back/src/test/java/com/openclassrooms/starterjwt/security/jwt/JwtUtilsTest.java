package com.openclassrooms.starterjwt.security.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtilsTest {

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetailsImpl userDetails;

    @InjectMocks
    private JwtUtils jwtUtils;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        setField(jwtUtils, "jwtSecret", "testSecret");
        setField(jwtUtils, "jwtExpirationMs", 3600000);
    }

    private void setField(Object target, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    public void testGenerateJwtToken() {
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testuser");

        String token = jwtUtils.generateJwtToken(authentication);

        Claims claims = Jwts.parser().setSigningKey("testSecret").parseClaimsJws(token).getBody();
        assertEquals("testuser", claims.getSubject());
        assertTrue(claims.getExpiration().after(new Date()));
    }

    @Test
    public void testGetUserNameFromJwtToken() {
        String token = Jwts.builder()
                .setSubject("testuser")
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 3600000))
                .signWith(SignatureAlgorithm.HS512, "testSecret")
                .compact();

        String username = jwtUtils.getUserNameFromJwtToken(token);
        assertEquals("testuser", username);
    }

    @Test
    public void testValidateJwtToken_ValidToken() {
        String token = Jwts.builder()
                .setSubject("testuser")
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 3600000))
                .signWith(SignatureAlgorithm.HS512, "testSecret")
                .compact();

        assertTrue(jwtUtils.validateJwtToken(token));
    }
}

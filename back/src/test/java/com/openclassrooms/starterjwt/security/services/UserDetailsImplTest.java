package com.openclassrooms.starterjwt.security.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

public class UserDetailsImplTest {

    private UserDetailsImpl userDetails;

    @BeforeEach
    public void setUp() {
        userDetails = UserDetailsImpl.builder()
                .id(1L)
                .username("testuser")
                .firstName("Test")
                .lastName("User")
                .admin(true)
                .password("password")
                .build();
    }

    @Test
    public void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertTrue(authorities.isEmpty(), "Authorities should be empty");
    }

    @Test
    public void testIsAccountNonExpired() {
        assertTrue(userDetails.isAccountNonExpired(), "Account should be non-expired");
    }

    @Test
    public void testIsAccountNonLocked() {
        assertTrue(userDetails.isAccountNonLocked(), "Account should be non-locked");
    }

    @Test
    public void testIsCredentialsNonExpired() {
        assertTrue(userDetails.isCredentialsNonExpired(), "Credentials should be non-expired");
    }

    @Test
    public void testIsEnabled() {
        assertTrue(userDetails.isEnabled(), "Account should be enabled");
    }

    @Test
    public void testEquals() {
        UserDetailsImpl userDetails2 = UserDetailsImpl.builder()
                .id(1L)
                .username("otheruser")
                .firstName("Other")
                .lastName("User")
                .admin(false)
                .password("otherpassword")
                .build();
        assertTrue(userDetails.equals(userDetails2), "Users with the same ID should be equal");
    }
}

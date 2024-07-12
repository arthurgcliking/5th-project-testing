package com.openclassrooms.starterjwt.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .password("password")
                .admin(true)
                .build();
    }

    @Test
    public void testUserBuilder() {
        assertEquals("test@example.com", user.getEmail());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("password", user.getPassword());
        assertTrue(user.isAdmin());
    }

    @Test
    public void testSettersAndGetters() {
        user.setId(1L);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        assertEquals(1L, user.getId());
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
    }

    @Test
    public void testEqualsAndHashCode() {
        User user1 = User.builder()
                .email("test1@example.com")
                .firstName("Jane")
                .lastName("Doe")
                .password("password1")
                .admin(false)
                .build();
        user1.setId(1L);

        User user2 = User.builder()
                .email("test2@example.com")
                .firstName("Jack")
                .lastName("Doe")
                .password("password2")
                .admin(true)
                .build();
        user2.setId(1L);

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void testToString() {
        String userString = user.toString();
        assertTrue(userString.contains("email=test@example.com"));
        assertTrue(userString.contains("firstName=John"));
        assertTrue(userString.contains("lastName=Doe"));
        assertTrue(userString.contains("admin=true"));
    }
}

package com.openclassrooms.starterjwt.payload.response;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JwtResponseTest {

    private JwtResponse jwtResponse;

    @BeforeEach
    public void setUp() {
        jwtResponse = new JwtResponse("token", 1L, "testuser", "Test", "User", true);
    }

    @Test
    public void testGetToken() {
        assertEquals("token", jwtResponse.getToken());
    }

    @Test
    public void testGetType() {
        assertEquals("Bearer", jwtResponse.getType());
    }

    @Test
    public void testGetId() {
        assertEquals(1L, jwtResponse.getId());
    }

    @Test
    public void testGetUsername() {
        assertEquals("testuser", jwtResponse.getUsername());
    }

    @Test
    public void testGetFirstName() {
        assertEquals("Test", jwtResponse.getFirstName());
    }

    @Test
    public void testGetLastName() {
        assertEquals("User", jwtResponse.getLastName());
    }

    @Test
    public void testGetAdmin() {
        assertEquals(true, jwtResponse.getAdmin());
    }

    @Test
    public void testSetToken() {
        jwtResponse.setToken("newToken");
        assertEquals("newToken", jwtResponse.getToken());
    }

    @Test
    public void testSetId() {
        jwtResponse.setId(2L);
        assertEquals(2L, jwtResponse.getId());
    }

    @Test
    public void testSetUsername() {
        jwtResponse.setUsername("newuser");
        assertEquals("newuser", jwtResponse.getUsername());
    }

    @Test
    public void testSetFirstName() {
        jwtResponse.setFirstName("New");
        assertEquals("New", jwtResponse.getFirstName());
    }

    @Test
    public void testSetLastName() {
        jwtResponse.setLastName("Name");
        assertEquals("Name", jwtResponse.getLastName());
    }

    @Test
    public void testSetAdmin() {
        jwtResponse.setAdmin(false);
        assertEquals(false, jwtResponse.getAdmin());
    }
}

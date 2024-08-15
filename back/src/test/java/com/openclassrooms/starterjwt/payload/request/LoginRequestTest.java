package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidLoginRequest() {
        LoginRequest request = new LoginRequest();
        request.setEmail("valid.email@example.com");
        request.setPassword("validPassword");

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty(), "There should be no constraint violations");
    }

    @Test
    void testInvalidEmail() {
        LoginRequest request = new LoginRequest();
        request.setEmail(""); // Empty email
        request.setPassword("validPassword");

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "There should be a constraint violation for empty email");
    }

    @Test
    void testInvalidPassword() {
        LoginRequest request = new LoginRequest();
        request.setEmail("valid.email@example.com");
        request.setPassword(""); // Empty password

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "There should be a constraint violation for empty password");
    }

    @Test
    void testGettersAndSetters() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test.email@example.com");
        request.setPassword("testPassword");

        assertEquals("test.email@example.com", request.getEmail());
        assertEquals("testPassword", request.getPassword());
    }

    @Test
    public void testNullEmail() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void testNullPassword() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        assertFalse(violations.isEmpty());
    }
}

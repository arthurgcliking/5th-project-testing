package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SignupRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidSignupRequest() {
        SignupRequest request = new SignupRequest();
        request.setEmail("valid.email@example.com");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setPassword("validPassword123");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty(), "There should be no constraint violations");
    }

    @Test
    void testInvalidEmail() {
        SignupRequest request = new SignupRequest();
        request.setEmail("invalid-email"); // Invalid email format
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setPassword("validPassword123");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "There should be a constraint violation for invalid email");
    }

    @Test
    void testEmptyFirstName() {
        SignupRequest request = new SignupRequest();
        request.setEmail("valid.email@example.com");
        request.setFirstName(""); // Empty first name
        request.setLastName("Doe");
        request.setPassword("validPassword123");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "There should be a constraint violation for empty first name");
    }

    @Test
    void testShortFirstName() {
        SignupRequest request = new SignupRequest();
        request.setEmail("valid.email@example.com");
        request.setFirstName("Jo"); // Too short first name (min 3)
        request.setLastName("Doe");
        request.setPassword("validPassword123");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "There should be a constraint violation for short first name");
    }

    @Test
    void testLongLastName() {
        SignupRequest request = new SignupRequest();
        request.setEmail("valid.email@example.com");
        request.setFirstName("John");
        request.setLastName("DoeDoeDoeDoeDoeDoeDoeDoeDoeDoeDoeDoe"); // Too long last name (max 20)
        request.setPassword("validPassword123");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "There should be a constraint violation for long last name");
    }

    @Test
    void testInvalidPassword() {
        SignupRequest request = new SignupRequest();
        request.setEmail("valid.email@example.com");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setPassword("short"); // Too short password (min 6)

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "There should be a constraint violation for short password");
    }

    @Test
    void testGettersAndSetters() {
        SignupRequest request = new SignupRequest();
        request.setEmail("test.email@example.com");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setPassword("testPassword123");

        assertEquals("test.email@example.com", request.getEmail());
        assertEquals("John", request.getFirstName());
        assertEquals("Doe", request.getLastName());
        assertEquals("testPassword123", request.getPassword());
    }

    @Test
    public void testNullEmail() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void testNullFirstName() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setFirstName(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void testNullLastName() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setLastName(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void testNullPassword() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setPassword(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

        assertFalse(violations.isEmpty());
    }
}

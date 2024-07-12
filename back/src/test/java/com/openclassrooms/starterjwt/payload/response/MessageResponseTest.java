package com.openclassrooms.starterjwt.payload.response;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MessageResponseTest {

    private MessageResponse messageResponse;

    @BeforeEach
    public void setUp() {
        messageResponse = new MessageResponse("Initial message");
    }

    @Test
    public void testGetMessage() {
        assertEquals("Initial message", messageResponse.getMessage());
    }

    @Test
    public void testSetMessage() {
        messageResponse.setMessage("New message");
        assertEquals("New message", messageResponse.getMessage());
    }
}

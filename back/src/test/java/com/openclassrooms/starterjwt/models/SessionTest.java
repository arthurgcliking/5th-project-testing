package com.openclassrooms.starterjwt.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SessionTest {

    private Session session;
    private Teacher teacher;

    @BeforeEach
    public void setUp() {
        teacher = Teacher.builder()
                .firstName("John")
                .lastName("Doe")
                .build();
        session = Session.builder()
                .name("Yoga Session")
                .date(new Date())
                .description("A relaxing yoga session")
                .teacher(teacher)
                .build();
    }

    @Test
    public void testSessionBuilder() {
        assertEquals("Yoga Session", session.getName());
        assertNotNull(session.getDate());
        assertEquals("A relaxing yoga session", session.getDescription());
        assertEquals(teacher, session.getTeacher());
    }

    @Test
    public void testSettersAndGetters() {
        session.setId(1L);
        session.setCreatedAt(LocalDateTime.now());
        session.setUpdatedAt(LocalDateTime.now());

        assertEquals(1L, session.getId());
        assertNotNull(session.getCreatedAt());
        assertNotNull(session.getUpdatedAt());
    }

    @Test
    public void testEqualsAndHashCode() {
        Session session1 = Session.builder()
                .name("Yoga Session 1")
                .date(new Date())
                .description("A relaxing yoga session 1")
                .build();
        session1.setId(1L);

        Session session2 = Session.builder()
                .name("Yoga Session 2")
                .date(new Date())
                .description("A relaxing yoga session 2")
                .build();
        session2.setId(1L);

        assertEquals(session1, session2);
        assertEquals(session1.hashCode(), session2.hashCode());
    }

    @Test
    public void testToString() {
        String sessionString = session.toString();
        assertTrue(sessionString.contains("name=Yoga Session"));
        assertTrue(sessionString.contains("description=A relaxing yoga session"));
        assertTrue(sessionString.contains("teacher=Teacher"));
    }
}

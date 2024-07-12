package com.openclassrooms.starterjwt.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TeacherTest {

    private Teacher teacher;

    @BeforeEach
    public void setUp() {
        teacher = Teacher.builder()
                .firstName("John")
                .lastName("Doe")
                .build();
    }

    @Test
    public void testTeacherBuilder() {
        assertEquals("John", teacher.getFirstName());
        assertEquals("Doe", teacher.getLastName());
    }

    @Test
    public void testSettersAndGetters() {
        teacher.setId(1L);
        teacher.setCreatedAt(LocalDateTime.now());
        teacher.setUpdatedAt(LocalDateTime.now());

        assertEquals(1L, teacher.getId());
        assertNotNull(teacher.getCreatedAt());
        assertNotNull(teacher.getUpdatedAt());
    }

    @Test
    public void testEqualsAndHashCode() {
        Teacher teacher1 = Teacher.builder()
                .firstName("Jane")
                .lastName("Doe")
                .build();
        teacher1.setId(1L);

        Teacher teacher2 = Teacher.builder()
                .firstName("Jack")
                .lastName("Doe")
                .build();
        teacher2.setId(1L);

        assertEquals(teacher1, teacher2);
        assertEquals(teacher1.hashCode(), teacher2.hashCode());
    }

    @Test
    public void testToString() {
        String teacherString = teacher.toString();
        assertTrue(teacherString.contains("firstName=John"));
        assertTrue(teacherString.contains("lastName=Doe"));
    }
}

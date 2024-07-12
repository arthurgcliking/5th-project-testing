package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class TeacherServiceIntegrationTest {

    @Mock
    private TeacherRepository teacherRepository;

    private TeacherService teacherService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        teacherService = new TeacherService(teacherRepository);
    }

    @Test
    public void testFindAll_ReturnsAllTeachers() {
        // Arrange
        List<Teacher> teachers = Arrays.asList(new Teacher(), new Teacher());
        when(teacherRepository.findAll()).thenReturn(teachers);

        // Act
        List<Teacher> result = teacherService.findAll();

        // Assert
        assertEquals(teachers, result);
        verify(teacherRepository, times(1)).findAll();
    }

    @Test
    public void testFindById_ExistingId_ReturnsTeacher() {
        // Arrange
        Long teacherId = 1L;
        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));

        // Act
        Teacher result = teacherService.findById(teacherId);

        // Assert
        assertEquals(teacher, result);
        verify(teacherRepository, times(1)).findById(teacherId);
    }

    @Test
    public void testFindById_NonExistingId_ReturnsNull() {
        // Arrange
        Long teacherId = 1L;
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.empty());

        // Act
        Teacher result = teacherService.findById(teacherId);

        // Assert
        assertNull(result);
        verify(teacherRepository, times(1)).findById(teacherId);
    }
}

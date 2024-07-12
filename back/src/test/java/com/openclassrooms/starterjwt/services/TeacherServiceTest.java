package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllTeachers() {
        Teacher teacher1 = new Teacher();
        Teacher teacher2 = new Teacher();
        List<Teacher> teachers = Arrays.asList(teacher1, teacher2);

        when(teacherRepository.findAll()).thenReturn(teachers);

        List<Teacher> foundTeachers = teacherService.findAll();

        assertEquals(2, foundTeachers.size());
    }

    @Test
    public void testFindTeacherById() {
        Long teacherId = 1L;
        Teacher teacher = new Teacher();
        teacher.setId(teacherId);

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));

        Teacher foundTeacher = teacherService.findById(teacherId);

        assertNotNull(foundTeacher);
        assertEquals(teacherId, foundTeacher.getId());
    }
}

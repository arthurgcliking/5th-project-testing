package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.TeacherService;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class SessionMapperTest {

    @Mock
    private TeacherService teacherService;

    @Mock
    private UserService userService;

    @InjectMocks
    private SessionMapper sessionMapper = Mappers.getMapper(SessionMapper.class);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testToEntity() {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Yoga Session");
        sessionDto.setDescription("A relaxing yoga session");
        sessionDto.setDate(new Date());
        sessionDto.setTeacher_id(1L);
        sessionDto.setUsers(Arrays.asList(1L, 2L));

        Teacher teacher = new Teacher();
        teacher.setId(1L);

        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);

        when(teacherService.findById(1L)).thenReturn(teacher);
        when(userService.findById(1L)).thenReturn(user1);
        when(userService.findById(2L)).thenReturn(user2);

        Session session = sessionMapper.toEntity(sessionDto);

        assertEquals(sessionDto.getName(), session.getName());
        assertEquals(sessionDto.getDescription(), session.getDescription());
        assertEquals(sessionDto.getDate(), session.getDate());
        assertEquals(teacher, session.getTeacher());
        assertEquals(2, session.getUsers().size());
    }

    @Test
    public void testToDto() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");

        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);

        Session session = new Session();
        session.setName("Yoga Session");
        session.setDescription("A relaxing yoga session");
        session.setDate(new Date());
        session.setTeacher(teacher);
        session.setUsers(Arrays.asList(user1, user2));

        SessionDto sessionDto = sessionMapper.toDto(session);

        assertEquals(session.getName(), sessionDto.getName());
        assertEquals(session.getDescription(), sessionDto.getDescription());
        assertEquals(session.getDate(), sessionDto.getDate());
        assertEquals(1L, sessionDto.getTeacher_id());
        assertEquals(2, sessionDto.getUsers().size());
    }
}

package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SessionService sessionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateSession() {
        Session session = new Session();
        when(sessionRepository.save(session)).thenReturn(session);

        Session createdSession = sessionService.create(session);

        assertNotNull(createdSession);
        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    public void testDeleteSession() {
        Long sessionId = 1L;
        doNothing().when(sessionRepository).deleteById(sessionId);

        sessionService.delete(sessionId);

        verify(sessionRepository, times(1)).deleteById(sessionId);
    }

    @Test
    public void testFindAllSessions() {
        when(sessionRepository.findAll()).thenReturn(Arrays.asList(new Session(), new Session()));

        assertEquals(2, sessionService.findAll().size());
    }

    @Test
    public void testGetSessionById() {
        Long sessionId = 1L;
        Session session = new Session();
        session.setId(sessionId);

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));

        assertNotNull(sessionService.getById(sessionId));
    }

    @Test
    public void testParticipateInSession() {
        Long sessionId = 1L;
        Long userId = 1L;
        Session session = new Session();
        session.setId(sessionId);
        session.setUsers(new ArrayList<>());
        User user = new User();
        user.setId(userId);

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        sessionService.participate(sessionId, userId);

        assertTrue(session.getUsers().contains(user));
        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    public void testParticipateInSession_NotFound() {
        Long sessionId = 1L;
        Long userId = 1L;

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> sessionService.participate(sessionId, userId));
    }

    @Test
    public void testNoLongerParticipateInSession() {
        Long sessionId = 1L;
        Long userId = 1L;
        Session session = new Session();
        User user = new User();
        user.setId(userId);
        session.setUsers(Arrays.asList(user));

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));

        sessionService.noLongerParticipate(sessionId, userId);

        assertFalse(session.getUsers().contains(user));
        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    public void testNoLongerParticipateInSession_NotFound() {
        Long sessionId = 1L;
        Long userId = 1L;

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> sessionService.noLongerParticipate(sessionId, userId));
    }
}

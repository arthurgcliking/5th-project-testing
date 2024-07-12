package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class SessionServiceIntegrationTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    private SessionService sessionService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        sessionService = new SessionService(sessionRepository, userRepository);
    }

    @Test
    public void testCreate_SavesSession() {
        // Arrange
        Session session = new Session();
        when(sessionRepository.save(session)).thenReturn(session);

        // Act
        Session result = sessionService.create(session);

        // Assert
        assertEquals(session, result);
        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    public void testDelete_ExistingId_DeletesSession() {
        // Arrange
        Long sessionId = 1L;

        // Act
        sessionService.delete(sessionId);

        // Assert
        verify(sessionRepository, times(1)).deleteById(sessionId);
    }

    @Test
    public void testFindAll_ReturnsAllSessions() {
        // Arrange
        List<Session> sessions = Arrays.asList(new Session(), new Session());
        when(sessionRepository.findAll()).thenReturn(sessions);

        // Act
        List<Session> result = sessionService.findAll();

        // Assert
        assertEquals(sessions, result);
        verify(sessionRepository, times(1)).findAll();
    }

    @Test
    public void testGetById_ExistingId_ReturnsSession() {
        // Arrange
        Long sessionId = 1L;
        Session session = new Session();
        session.setId(sessionId);
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));

        // Act
        Session result = sessionService.getById(sessionId);

        // Assert
        assertEquals(session, result);
        verify(sessionRepository, times(1)).findById(sessionId);
    }

    @Test
    public void testGetById_NonExistingId_ReturnsNull() {
        // Arrange
        Long sessionId = 1L;
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());

        // Act
        Session result = sessionService.getById(sessionId);

        // Assert
        assertNull(result);
        verify(sessionRepository, times(1)).findById(sessionId);
    }

    @Test
    public void testUpdate_ExistingId_UpdatesSession() {
        // Arrange
        Long sessionId = 1L;
        Session session = new Session();
        when(sessionRepository.save(session)).thenReturn(session);

        // Act
        Session result = sessionService.update(sessionId, session);

        // Assert
        assertEquals(session, result);
        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    public void testParticipate_ExistingSessionAndUser_AddsUserToSession() {
        // Arrange
        Long sessionId = 1L;
        Long userId = 1L;
        Session session = new Session();
        session.setUsers(new ArrayList<>()); // Initialize the users list
        User user = new User();
        user.setId(userId);
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        sessionService.participate(sessionId, userId);

        // Assert
        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    public void testParticipate_NonExistingSessionOrUser_ThrowsNotFoundException() {
        // Arrange
        Long sessionId = 1L;
        Long userId = 1L;
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> sessionService.participate(sessionId, userId));
    }

    @Test
    public void testParticipate_UserAlreadyParticipates_ThrowsBadRequestException() {
        // Arrange
        Long sessionId = 1L;
        Long userId = 1L;
        Session session = new Session();
        session.setUsers(new ArrayList<>()); // Initialize the users list
        User user = new User();
        user.setId(userId);
        session.getUsers().add(user);
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act & Assert
        assertThrows(BadRequestException.class, () -> sessionService.participate(sessionId, userId));
    }

    @Test
    public void testNoLongerParticipate_ExistingSessionAndUser_RemovesUserFromSession() {
        // Arrange
        Long sessionId = 1L;
        Long userId = 1L;
        Session session = new Session();
        session.setUsers(new ArrayList<>()); // Initialize the users list
        User user = new User();
        user.setId(userId);
        session.getUsers().add(user);
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));

        // Act
        sessionService.noLongerParticipate(sessionId, userId);

        // Assert
        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    public void testNoLongerParticipate_NonExistingSession_ThrowsNotFoundException() {
        // Arrange
        Long sessionId = 1L;
        Long userId = 1L;
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> sessionService.noLongerParticipate(sessionId, userId));
    }

    @Test
    public void testNoLongerParticipate_UserNotParticipating_ThrowsBadRequestException() {
        // Arrange
        Long sessionId = 1L;
        Long userId = 1L;
        Session session = new Session();
        session.setUsers(new ArrayList<>()); // Initialize the users list
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));

        // Act & Assert
        assertThrows(BadRequestException.class, () -> sessionService.noLongerParticipate(sessionId, userId));
    }
}

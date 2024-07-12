package com.openclassrooms.starterjwt.controllers;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.services.SessionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Date;

public class SessionControllerTest {

    @Mock
    private SessionService sessionService;

    @Mock
    private SessionMapper sessionMapper;

    @InjectMocks
    private SessionController sessionController;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sessionController).build();
    }

    @Test
    public void testFindById() throws Exception {
        Session session = new Session();
        SessionDto sessionDto = new SessionDto();
        when(sessionService.getById(anyLong())).thenReturn(session);
        when(sessionMapper.toDto((Session) any())).thenReturn(sessionDto);

        mockMvc.perform(get("/api/session/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindAll() throws Exception {
        when(sessionService.findAll()).thenReturn(Collections.singletonList(new Session()));
        when(sessionMapper.toDto(anyList())).thenReturn(Collections.singletonList(new SessionDto()));

        mockMvc.perform(get("/api/session"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateSession() throws Exception {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Yoga Session");
        sessionDto.setDescription("A relaxing yoga session");
        sessionDto.setDate(new Date());
        sessionDto.setTeacher_id(1L);

        Session session = new Session();
        when(sessionService.create(any(Session.class))).thenReturn(session);
        when(sessionMapper.toDto(any(Session.class))).thenReturn(sessionDto);
        when(sessionMapper.toEntity(any(SessionDto.class))).thenReturn(session);

        mockMvc.perform(post("/api/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateSession() throws Exception {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Updated Yoga Session");
        sessionDto.setDescription("An updated relaxing yoga session");
        sessionDto.setDate(new Date());
        sessionDto.setTeacher_id(1L);

        Session session = new Session();
        when(sessionService.update(anyLong(), any(Session.class))).thenReturn(session);
        when(sessionMapper.toDto(any(Session.class))).thenReturn(sessionDto);
        when(sessionMapper.toEntity(any(SessionDto.class))).thenReturn(session);

        mockMvc.perform(put("/api/session/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteSession() throws Exception {
        when(sessionService.getById(anyLong())).thenReturn(new Session());

        mockMvc.perform(delete("/api/session/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testParticipateInSession() throws Exception {
        mockMvc.perform(post("/api/session/1/participate/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testNoLongerParticipateInSession() throws Exception {
        mockMvc.perform(delete("/api/session/1/participate/1"))
                .andExpect(status().isOk());
    }
}

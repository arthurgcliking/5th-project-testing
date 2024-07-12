package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/sql/script.sql")
public class SessionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        // Any setup needed before each test
    }

    @Test
    @WithMockUser(username = "yoga@studio.com", roles = {"ADMIN"})
    void testFindById() throws Exception {
        // Arrange
        Session session = new Session();
        session.setName("Yoga Class");
        session.setDescription("A relaxing yoga session.");
        session.setDate(new Date());
        sessionRepository.save(session);

        // Act
        MvcResult result = mockMvc.perform(get("/api/session/" + session.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        System.out.println("FindById Response: " + result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(username = "yoga@studio.com", roles = {"ADMIN"})
    void testFindAll() throws Exception {
        // Act
        MvcResult result = mockMvc.perform(get("/api/session")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        System.out.println("FindAll Response: " + result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(username = "yoga@studio.com", roles = {"ADMIN"})
    void testCreateSession() throws Exception {
        // Arrange
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Yoga Class");
        sessionDto.setDescription("A relaxing yoga session.");
        sessionDto.setDate(new Date());

        // Act
        MvcResult result = mockMvc.perform(post("/api/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        System.out.println("CreateSession Response: " + result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(username = "yoga@studio.com", roles = {"ADMIN"})
    void testUpdateSession() throws Exception {
        // Arrange
        Session session = new Session();
        session.setName("Yoga Class");
        session.setDescription("A relaxing yoga session.");
        session.setDate(new Date());
        sessionRepository.save(session);

        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Updated Yoga Class");
        sessionDto.setDescription("An updated relaxing yoga session.");
        sessionDto.setDate(new Date());

        // Act
        MvcResult result = mockMvc.perform(put("/api/session/" + session.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        System.out.println("UpdateSession Response: " + result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(username = "yoga@studio.com", roles = {"ADMIN"})
    void testDeleteSession() throws Exception {
        // Arrange
        Session session = new Session();
        session.setName("Yoga Class");
        session.setDescription("A relaxing yoga session.");
        session.setDate(new Date());
        sessionRepository.save(session);

        // Act
        MvcResult result = mockMvc.perform(delete("/api/session/" + session.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        System.out.println("DeleteSession Response: " + result.getResponse().getContentAsString());
    }
}

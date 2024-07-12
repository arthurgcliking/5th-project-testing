// package com.openclassrooms.starterjwt.controllers;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.openclassrooms.starterjwt.payload.request.LoginRequest;
// import com.openclassrooms.starterjwt.payload.request.SignupRequest;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.context.jdbc.Sql;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.MvcResult;

// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @SpringBootTest
// @AutoConfigureMockMvc
// @Sql(scripts = "/sql/script.sql")
// public class AuthControllerIntegrationTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @Autowired
//     private ObjectMapper objectMapper;

//     @Test
//     void testUserSignup() throws Exception {
//         SignupRequest signupRequest = new SignupRequest();
//         signupRequest.setFirstName("John");
//         signupRequest.setLastName("Doe");
//         signupRequest.setEmail("john.doe@example.com");
//         signupRequest.setPassword("password");

//         MvcResult result = mockMvc.perform(post("/api/auth/register")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(signupRequest)))
//                 .andExpect(status().isOk())
//                 .andReturn();

//         System.out.println("Response: " + result.getResponse().getContentAsString());
//     }

//     @Test
//     void testUserLogin() throws Exception {
//         LoginRequest loginRequest = new LoginRequest();
//         loginRequest.setEmail("yoga@studio.com");
//         loginRequest.setPassword("password");

//         MvcResult result = mockMvc.perform(post("/api/auth/login")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(loginRequest)))
//                 .andExpect(status().isOk())
//                 .andReturn();

//         System.out.println("Response: " + result.getResponse().getContentAsString());
//     }
// }

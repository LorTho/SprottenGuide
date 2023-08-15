package com.example.backend.controllers;

import com.example.backend.entities.MongoUser;
import com.example.backend.model.user.UserDTO;
import com.example.backend.security.UserSecurity;
import com.example.backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserService userService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DirtiesContext
    @WithMockUser
    void postNewEmployee() throws Exception {
        MongoUser newMongoUser = new MongoUser("1111", "test", "test", "");
        String expectedEmployee = objectMapper.writeValueAsString(newMongoUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/add")
                        .contentType(MediaType.APPLICATION_JSON).content("""
                                {
                                    "memberCode": "1111",
                                    "firstName": "test",
                                    "lastName": "test",
                                    "password": ""
                                }
                                """)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedEmployee));
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void getEmployee() throws Exception {
        UserSecurity newMongoUser = new UserSecurity("1111", "test", "test", "1111");
        UserDTO userDTO = new UserDTO(newMongoUser.memberCode(), newMongoUser.firstName(), newMongoUser.lastName());
        String expected = objectMapper.writeValueAsString(userDTO);
        userService.addUser(newMongoUser);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/"+"1111")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }
    @Test
    @DirtiesContext
    @WithMockUser
    void getListOfEmployees() throws Exception {
        UserSecurity newMongoUser1 = new UserSecurity("1111", "test", "test", "");
        UserSecurity newMongoUser2 = new UserSecurity("2222", "test", "test", "");
        userService.addUser(newMongoUser1);
        userService.addUser(newMongoUser2);


        List<UserDTO> expectedList = new ArrayList<>(List.of(
                new UserDTO(newMongoUser1.memberCode(), newMongoUser1.firstName(), newMongoUser1.lastName()),
                new UserDTO(newMongoUser2.memberCode(), newMongoUser2.firstName(), newMongoUser2.lastName())));
        String expected = objectMapper.writeValueAsString(expectedList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/list")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }
    @Test
    @WithMockUser(username = "0000")
    void getUsername_whenEndpointIsCalled() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(
                        "0000"));
    }
    @Test
    void getAnonymousUser_whenEndpointIsCalled() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(
                        "anonymousUser"));
    }

    @Test
    @WithMockUser(username = "hans")
    void getUsername_whenLoggingIn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(
                        "hans"));
    }
}

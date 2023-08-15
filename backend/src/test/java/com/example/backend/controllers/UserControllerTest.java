package com.example.backend.controllers;

import com.example.backend.entities.User;
import com.example.backend.model.user.UserDTO;
import com.example.backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    void postNewEmployee() throws Exception {
        User newUser = new User("1111", "test", "test", "");
        String expectedEmployee = objectMapper.writeValueAsString(newUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON).content("""
                                {
                                    "memberCode": "1111",
                                    "firstName": "test",
                                    "lastName": "test"
                                }
                                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedEmployee));
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void getEmployee() throws Exception {
        User newUser = new User("1111", "test", "test", "1111");
        UserDTO userDTO = new UserDTO(newUser.getId(), newUser.getFirstName(), newUser.getLastName());
        String expected = objectMapper.writeValueAsString(userDTO);
        userService.addUser(newUser);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/"+"1111")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }
    @Test
    @DirtiesContext
    void getListOfEmployees() throws Exception {
        User newUser1 = new User("1111", "test", "test", "");
        User newUser2 = new User("2222", "test", "test", "");
        userService.addUser(newUser1);
        userService.addUser(newUser2);


        List<UserDTO> expectedList = new ArrayList<>(List.of(
                new UserDTO(newUser1.getId(), newUser1.getFirstName(), newUser1.getLastName()),
                new UserDTO(newUser2.getId(), newUser2.getFirstName(), newUser2.getLastName())));
        String expected = objectMapper.writeValueAsString(expectedList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }
}

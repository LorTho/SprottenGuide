package com.example.backend.controllers;

import com.example.backend.entities.MongoUser;
import com.example.backend.model.user.UserDTO;
import com.example.backend.security.LoginData;
import com.example.backend.security.Role;
import com.example.backend.security.UserSecurity;
import com.example.backend.security.jwt.JwtService;
import com.example.backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DirtiesContext
    @WithMockUser
    void postNewEmployee() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/add")
                        .contentType(MediaType.APPLICATION_JSON).content("""
                                {
                                    "memberCode": "1111",
                                    "firstName": "test",
                                    "lastName": "test",
                                    "password": "",
                                    "role": "USER"
                                }
                                """))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void getEmployee() throws Exception {
        UserSecurity newMongoUser = new UserSecurity("1111", "test", "test", "1111", Role.USER);
        UserDTO userDTO = new UserDTO(newMongoUser.memberCode(), newMongoUser.firstName(), newMongoUser.lastName(), newMongoUser.role());
        String expected = objectMapper.writeValueAsString(userDTO);
        userService.addUser(newMongoUser);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/"+"1111"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }
    @Test
    @DirtiesContext
    @WithMockUser
    void getListOfEmployees() throws Exception {
        UserSecurity newMongoUser1 = new UserSecurity("1111", "test", "test", "", Role.USER);
        UserSecurity newMongoUser2 = new UserSecurity("2222", "test", "test", "", Role.ADMIN);
        userService.addUser(newMongoUser1);
        userService.addUser(newMongoUser2);


        List<UserDTO> expectedList = new ArrayList<>(List.of(
                new UserDTO(newMongoUser1.memberCode(), newMongoUser1.firstName(), newMongoUser1.lastName(), newMongoUser1.role()),
                new UserDTO(newMongoUser2.memberCode(), newMongoUser2.firstName(), newMongoUser2.lastName(), newMongoUser2.role())));
        String expected = objectMapper.writeValueAsString(expectedList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/list"))
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
    @DirtiesContext
    @WithMockUser(username = "test")
    void getUsername_whenLoggingIn() throws Exception {
        userService.addUser(new UserSecurity("1111", "test", "test", "1234", Role.USER));
        LoginData loginData = new LoginData("1111", "1234");
        String data = objectMapper.writeValueAsString(loginData);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(data))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(org.hamcrest.Matchers.notNullValue(String.class)));
    }

    @Test
    @DirtiesContext

    void expectHeader_whenFetchMe() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + getToken()))
                // THEN
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("testId"));
    }
    private String getToken() throws Exception {
        userService.addUser(new UserSecurity("testId", "testUser", "testUser", "testPassword", Role.USER));
        LoginData loginData = new LoginData("testId", "testPassword");
        String data = objectMapper.writeValueAsString(loginData);
        return mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    @DirtiesContext
    void expectAnonymous_whenGettingUserInfo() throws Exception {
        String expected = "anonymousUser";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expected));
    }
}

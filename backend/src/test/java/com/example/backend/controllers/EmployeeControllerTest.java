package com.example.backend.controllers;

import com.example.backend.entities.Employee;
import com.example.backend.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    EmployeeService employeeService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DirtiesContext
    void postNewEmployee() throws Exception {
        Employee newEmployee = new Employee("1111", "test", "test");
        String expectedEmployee = objectMapper.writeValueAsString(newEmployee);

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
    void getEmployee() throws Exception {
        Employee newEmployee = new Employee("1111", "test", "test");
        String expected = objectMapper.writeValueAsString(newEmployee);
        employeeService.addEmployee(newEmployee);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/"+"1111"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }
    @Test
    @DirtiesContext
    void getListOfEmployees() throws Exception {
        Employee newEmployee1 = new Employee("1111", "test", "test");
        Employee newEmployee2 = new Employee("2222", "test", "test");
        employeeService.addEmployee(newEmployee1);
        employeeService.addEmployee(newEmployee2);


        List<Employee> expectedList = new ArrayList<>(List.of(newEmployee1, newEmployee2));
        String expected = objectMapper.writeValueAsString(expectedList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }
}

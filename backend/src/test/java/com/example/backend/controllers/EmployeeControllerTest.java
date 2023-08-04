package com.example.backend.controllers;

import com.example.backend.model.employee.Employee;
import com.example.backend.model.employee.EmployeeWithoutShifts;
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
    void putNewEmployee() throws Exception {
        Employee newEmployee = new Employee("1111", "test", "test",new ArrayList<>(), new ArrayList<>());
        String expectedEmployee = objectMapper.writeValueAsString(newEmployee);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON).content("""
                                {
                                    "id": "1111",
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
        Employee newEmployee = new Employee("1111", "test", "test",new ArrayList<>(), new ArrayList<>());
        String expected = objectMapper.writeValueAsString(newEmployee);
        employeeService.addEmployee(newEmployee);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/"+"1111"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }
    @Test
    @DirtiesContext
    void getListOfEmployees() throws Exception {
        Employee newEmployee1 = new Employee("1111", "test", "test",new ArrayList<>(), new ArrayList<>());
        Employee newEmployee2 = new Employee("2222", "test", "test",new ArrayList<>(), new ArrayList<>());
        employeeService.addEmployee(newEmployee1);
        employeeService.addEmployee(newEmployee2);

        EmployeeWithoutShifts employee1 = new EmployeeWithoutShifts();
        employee1.setId(newEmployee1.getId());
        employee1.setFirstName(newEmployee1.getFirstName());
        employee1.setLastName(newEmployee1.getLastName());
        EmployeeWithoutShifts employee2 = new EmployeeWithoutShifts();
        employee2.setId(newEmployee2.getId());
        employee2.setFirstName(newEmployee2.getFirstName());
        employee2.setLastName(newEmployee2.getLastName());

        List<EmployeeWithoutShifts> expectedList = new ArrayList<>(List.of(employee1, employee2));
        String expected = objectMapper.writeValueAsString(expectedList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }

    @Test
    @DirtiesContext
    void getUpdatedEmployee_whenChangeWishShifts() throws Exception {
        Employee newEmployee = new Employee("1111", "test", "test",new ArrayList<>(), new ArrayList<>());
        employeeService.addEmployee(newEmployee);
        String expected = """
            {
                "id": "1111",
                "firstName": "test",
                "lastName": "test",
                "thisWeek": [],
                "nextWeek": [
                    {
                        "day": "MONDAY",
                        "startTime": "11:00:00"
                    },
                    {
                        "day": "FRIDAY",
                        "startTime": "17:00:00"
                    }
                ]
            }
        """;
        mockMvc.perform(MockMvcRequestBuilders.put("/api/employee/"+"1111")
                .contentType(MediaType.APPLICATION_JSON).content("""
                [
                    {
                        "day": "MONDAY",
                        "startTime": "11:00"
                    },
                    {
                        "day": "FRIDAY",
                        "startTime": "17:00"
                    }
                ]
                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }
}

package com.example.backend.controllers;

import com.example.backend.model.Employee;
import com.example.backend.model.Time;
import com.example.backend.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    private MongoTemplate mongoTemplate;

    ObjectMapper objectMapper = new ObjectMapper();
    @Test
    void getWeeklyTime() throws Exception {
        ArrayList<Map<Integer, List<Time>>> weekly1 = null;
        HashMap<Integer, List<Time>> weekly2 = null;
        weekly2.put(30,
                List.of(
                        new Time(LocalDate.of(2023, 7, 24), "10.00 Uhr"),
                        new Time(LocalDate.of(2023, 7, 25), "10.00 Uhr"),
                        new Time(LocalDate.of(2023, 7, 26), "10.00 Uhr"),
                        new Time(LocalDate.of(2023, 7, 27), "10.00 Uhr")
                        ));
        weekly1.add(weekly2);
        Employee emp1 = new Employee("123", "Lorenz", "Thoms", weekly1);
        mongoTemplate.save(emp1);
        String expected = objectMapper.writeValueAsString(emp1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/123"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }
}
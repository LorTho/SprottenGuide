package com.example.backend.controllers;

import com.example.backend.model.Employee;
import com.example.backend.model.Time;
import com.example.backend.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Test
    void getWeeklyTime() throws Exception {
        HashMap<String, List<Time>> weekly = new HashMap<>();
        weekly.put("kw30",
                List.of(new Time("24.07", "10.00 Uhr"),
                        new Time("25.07", "11.00 Uhr"),
                        new Time("26.07", "12.00 Uhr"),
                        new Time("27.07", "13.00 Uhr")
                        ));
        Employee emp1 = new Employee("123", "Lorenz", "Thoms", weekly);
        mongoTemplate.save(emp1);
        String expectedList = """
                [
                      {
                        "date": "24.07",
                        "startTime": "10.00 Uhr"
                      },
                      {
                        "date": "25.07",
                        "startTime": "11.00 Uhr"
                      },
                      {
                        "date": "26.07",
                        "startTime": "12.00 Uhr"
                      },
                      {
                        "date": "27.07",
                        "startTime": "13.00 Uhr"
                      }
                ]
                """;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/123/kw30"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedList));
    }
}
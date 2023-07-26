package com.example.backend.controllers;

import com.example.backend.model.Employee;
import com.example.backend.model.WeekInitializer;
import com.example.backend.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


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
    void getNewEmployee() throws Exception {
        Employee expectedEmployee = new Employee("noId", "test", "test", WeekInitializer.createWeek(0), WeekInitializer.createWeek(7));
        String expected = """
            {
                "firstName":"test",
                "lastName":"test",
                "thisWeek":
                    {"30":
                        [{"date":"2023-07-24","startTime":"00:00:00"},
                        {"date":"2023-07-25","startTime":"00:00:00"},
                        {"date":"2023-07-26","startTime":"00:00:00"},
                        {"date":"2023-07-27","startTime":"00:00:00"},
                        {"date":"2023-07-28","startTime":"00:00:00"},
                        {"date":"2023-07-29","startTime":"00:00:00"},
                        {"date":"2023-07-30","startTime":"00:00:00"}]},
                "nextWeek":
                    {"31":
                        [{"date":"2023-07-31","startTime":"00:00:00"},
                        {"date":"2023-08-01","startTime":"00:00:00"},
                        {"date":"2023-08-02","startTime":"00:00:00"},
                        {"date":"2023-08-03","startTime":"00:00:00"},
                        {"date":"2023-08-04","startTime":"00:00:00"},
                        {"date":"2023-08-05","startTime":"00:00:00"},
                        {"date":"2023-08-06","startTime":"00:00:00"}]}}
            """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON).content("""
                                {
                                    "firstName": "test",
                                    "lastName": "test"
                                }
                                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }
}
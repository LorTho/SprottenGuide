package com.example.backend.controllers;

import com.example.backend.entities.MonthlyPlan;
import com.example.backend.model.monthly.Daily;
import com.example.backend.model.monthly.DailyPlan;
import com.example.backend.service.MonthlyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class MonthControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    MonthlyService monthlyService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getToday() throws Exception {
        MonthlyPlan newPlan = new MonthlyPlan("SomeID", LocalDate.now().getMonth(), List.of(
                new Daily(LocalDate.now(), new ArrayList<>())
        ));
        monthlyService.add(newPlan);

        Daily expected = new Daily();
        expected.setDay(LocalDate.now());
        expected.setDailyPlanList(new ArrayList<>());

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String expectedJson = objectMapper.writeValueAsString(expected);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/month/today"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedJson));
    }
}
package com.example.backend.controllers;

import com.example.backend.model.WorkSchedule;
import com.example.backend.service.ScheduleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ScheduleControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ScheduleService scheduleService;
    ObjectMapper objectMapper= new ObjectMapper();
    @Test
    void getWorkSchedule() throws Exception {
        WorkSchedule workSchedule = new WorkSchedule("kw 30", new ArrayList<>(), new ArrayList<>());
        String expected = objectMapper.writeValueAsString(List.of(workSchedule));
        scheduleService.addWorkSchedule(workSchedule);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/schedule"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }
}
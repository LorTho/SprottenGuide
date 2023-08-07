package com.example.backend.controllers;

import com.example.backend.model.schedule.ShiftSchedule;
import com.example.backend.entities.WorkSchedule;
import com.example.backend.model.schedule.WorkScheduleNoId;
import com.example.backend.model.shift.WorkShift;
import com.example.backend.service.ScheduleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mockStatic;

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
        WorkSchedule workSchedule = new WorkSchedule("SomeID", "SomeName", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        WorkScheduleNoId expectedSchedule = new WorkScheduleNoId("SomeName", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        String expected = objectMapper.writeValueAsString(expectedSchedule);
        scheduleService.addWorkSchedule(workSchedule);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/schedule/SomeName"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }

    @Test
    void saveWorkSchedule() throws Exception {
        MockedStatic<LocalTime> mock = mockStatic(LocalTime.class);
        var time = LocalTime.of(11,0);
        mock.when(()->LocalTime.of(11,0)).thenReturn(time);

        WorkScheduleNoId expected = new WorkScheduleNoId("next", List.of(
                new ShiftSchedule("MONDAY", List.of(
                        new WorkShift("0000", LocalTime.of(11,0))
                ))), List.of(new ShiftSchedule("MONDAY", List.of(
                new WorkShift("0000", LocalTime.of(11,0))
        ))),
                new ArrayList<>());
        String expectedSchedule = objectMapper.writeValueAsString(expected);


        mockMvc.perform(MockMvcRequestBuilders.put("/api/schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "name": "next",
                        "drivers": [
                        {
                            "day": "MONDAY",
                            "shifts": [{"employeeId": "0000", "startTime":  "11:00:00"}]
                        }],
                        "kitchen": [{
                            "day": "MONDAY",
                            "shifts": [{"employeeId": "0000", "startTime":  "11:00:00"}]
                        }]
                    }
                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedSchedule));
    }
}

package com.example.backend.controllers;

import com.example.backend.model.schedule.ShiftSchedule;
import com.example.backend.entities.WorkSchedule;
import com.example.backend.model.schedule.WishSchedule;
import com.example.backend.model.schedule.WorkScheduleNoId;
import com.example.backend.model.shift.Shifts;
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
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getWorkSchedule() throws Exception {
        WorkSchedule workSchedule = new WorkSchedule("SomeID", "SomeName", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        WorkScheduleNoId workScheduleNoId = new WorkScheduleNoId("SomeName", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        String expected = objectMapper.writeValueAsString(workScheduleNoId);
        scheduleService.addWorkSchedule(workSchedule);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/schedule/SomeName"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }

    @Test
    void saveWorkSchedule_withNewSchedule() throws Exception {
        MockedStatic<LocalTime> mock = mockStatic(LocalTime.class);
        var time = LocalTime.of(11, 0);
        mock.when(() -> LocalTime.of(11, 0)).thenReturn(time);

        WorkScheduleNoId expected = new WorkScheduleNoId("SomeName", List.of(
                new ShiftSchedule("MONDAY", List.of(
                        new WorkShift("0000", LocalTime.of(11, 0))
                ))),
                List.of(new ShiftSchedule("MONDAY", List.of(
                        new WorkShift("0000", LocalTime.of(11, 0))
                ))),
                new ArrayList<>());
        String expectedSchedule = objectMapper.writeValueAsString(expected);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "name": "SomeName",
                                        "drivers": [
                                        {
                                            "day": "MONDAY",
                                            "shifts": [{"employeeId": "0000", "startTime":  "11:00:00"}]
                                        }],
                                        "kitchen": [{
                                            "day": "MONDAY",
                                            "shifts": [{"employeeId": "0000", "startTime":  "11:00:00"}]
                                        }],
                                        "wishes": []
                                    }
                                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedSchedule));
        mock.close();
    }

    @Test
    void saveWorkSchedule_withExistingSchedule() throws Exception {
        MockedStatic<LocalTime> mock = mockStatic(LocalTime.class);
        var time = LocalTime.of(11, 0);
        mock.when(() -> LocalTime.of(11, 0)).thenReturn(time);

        WorkSchedule expected = new WorkSchedule("SomeId", "SomeName",
                new ArrayList<>(List.of(
                        new ShiftSchedule("MONDAY", List.of(
                                new WorkShift("0000", LocalTime.of(11, 0))
                        )))),
                new ArrayList<>(List.of(new ShiftSchedule("MONDAY", List.of(
                        new WorkShift("0000", LocalTime.of(11, 0))
                )))),
                new ArrayList<>());
        scheduleService.addWorkSchedule(expected);
        String expectedSchedule = objectMapper.writeValueAsString(expected);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "id": "SomeId",
                                        "name": "SomeName",
                                        "drivers": [
                                        {
                                            "day": "MONDAY",
                                            "shifts": [{"employeeId": "0000", "startTime":  "11:00:00"}]
                                        }],
                                        "kitchen": [{
                                            "day": "MONDAY",
                                            "shifts": [{"employeeId": "0000", "startTime":  "11:00:00"}]
                                        }],
                                        "wishes": []
                                    }
                                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedSchedule));
        mock.close();
    }

    @Test
    void getEmployeeWish_whenWishesExist() throws Exception {
        MockedStatic<LocalTime> mock = mockStatic(LocalTime.class);
        var time = LocalTime.of(11, 0);
        mock.when(() -> LocalTime.of(11, 0)).thenReturn(time);

        WorkSchedule workSchedule = new WorkSchedule("SomeID", "WishSchedule",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(List.of(
                        new WishSchedule("0000", new ArrayList<>(
                                List.of(
                                        new Shifts("MONDAY", LocalTime.of(11, 0)),
                                        new Shifts("THURSDAY", LocalTime.of(11, 0))
                                ))))));
        List<Shifts> expectedList = new ArrayList<>(List.of(
                new Shifts("MONDAY", LocalTime.of(11, 0)),
                new Shifts("THURSDAY", LocalTime.of(11, 0))
        ));
        scheduleService.addWorkSchedule(workSchedule);

        String expected = objectMapper.writeValueAsString(expectedList);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/schedule/0000/WishSchedule/wish"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
        mock.close();
    }

    @Test
    void getEmployeeWish_whenNoWishesExist() throws Exception {
        MockedStatic<LocalTime> mock = mockStatic(LocalTime.class);
        var time = LocalTime.of(11, 0);
        mock.when(() -> LocalTime.of(11, 0)).thenReturn(time);

        WorkSchedule workSchedule = new WorkSchedule("SomeID", "NoWishSchedule",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>());
        List<Shifts> expectedList = new ArrayList<>();
        scheduleService.addWorkSchedule(workSchedule);

        String expected = objectMapper.writeValueAsString(expectedList);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/schedule/0000/NoWishSchedule/wish"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
        mock.close();
    }

    @Test
    void getEmployeeShifts_whenShiftsExist() throws Exception {
        MockedStatic<LocalTime> mock = mockStatic(LocalTime.class);
        var time = LocalTime.of(11, 0);
        mock.when(() -> LocalTime.of(11, 0)).thenReturn(time);

        WorkSchedule workSchedule = new WorkSchedule("SomeID", "ShiftSchedule",
                new ArrayList<>(List.of(
                        new ShiftSchedule("MONDAY", new ArrayList<>(
                                List.of(
                                        new WorkShift("0000", LocalTime.of(11, 0))
                                ))))),
                new ArrayList<>(),
                new ArrayList<>());

        List<Shifts> expectedList = new ArrayList<>(List.of(
                new Shifts("MONDAY", LocalTime.of(11, 0))
        ));
        scheduleService.addWorkSchedule(workSchedule);

        String expected = objectMapper.writeValueAsString(expectedList);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/schedule/0000/ShiftSchedule"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
        mock.close();
    }

    @Test
    void saveEmployeeWish() throws Exception {
        MockedStatic<LocalTime> mock = mockStatic(LocalTime.class);
        var time = LocalTime.of(11, 0);
        mock.when(() -> LocalTime.of(11, 0)).thenReturn(time);

        WorkSchedule workSchedule = new WorkSchedule("SomeID", "saveWish",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(List.of(
                        new WishSchedule("0000", new ArrayList<>(
                                List.of(
                                        new Shifts("MONDAY", LocalTime.of(11, 0)),
                                        new Shifts("THURSDAY", LocalTime.of(11, 0))
                                ))))));

        List<Shifts> expectedList = new ArrayList<>(List.of(
                new Shifts("MONDAY", LocalTime.of(11, 0)),
                new Shifts("TUESDAY", LocalTime.of(11, 0))

        ));
        scheduleService.addWorkSchedule(workSchedule);

        String expected = objectMapper.writeValueAsString(expectedList);


        mockMvc.perform(MockMvcRequestBuilders.put("/api/schedule/0000/saveWish")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            [
                                {
                                    "day": "MONDAY",
                                    "startTime": "11:00:00"
                                },
                                {
                                    "day": "TUESDAY",
                                    "startTime": "11:00:00"
                                }
                            ]
                        """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
        mock.close();
    }
}

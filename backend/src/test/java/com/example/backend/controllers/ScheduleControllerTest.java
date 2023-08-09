package com.example.backend.controllers;

import com.example.backend.entities.WorkSchedule;
import com.example.backend.model.schedule.ShiftSchedule;
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

import java.time.LocalDate;
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
        WorkSchedule workSchedule = new WorkSchedule("SomeID", 30, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        WorkScheduleNoId workScheduleNoId = new WorkScheduleNoId(30, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        String expected = objectMapper.writeValueAsString(workScheduleNoId);
        scheduleService.addWorkSchedule(workSchedule);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/schedule/30"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }

    @Test
    void saveWorkSchedule_withNewSchedule() throws Exception {
        MockedStatic<LocalTime> mock = mockStatic(LocalTime.class);
        var time = LocalTime.of(11, 0);
        mock.when(() -> LocalTime.of(11, 0)).thenReturn(time);
        MockedStatic<LocalDate> mock2 = mockStatic(LocalDate.class);
        var day1 = LocalDate.of(2023,8,1);
        mock2.when(() -> LocalDate.of(2023,8,1)).thenReturn(day1);

        WorkScheduleNoId expected = new WorkScheduleNoId(30, List.of(
                new ShiftSchedule(LocalDate.of(2023,8,1), List.of(
                        new WorkShift("0000", LocalTime.of(11, 0))
                ))),
                List.of(new ShiftSchedule(LocalDate.of(2023,8,1), List.of(
                        new WorkShift("0000", LocalTime.of(11, 0))
                ))),
                new ArrayList<>());
        String expectedSchedule = objectMapper.writeValueAsString(expected);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "name": 30,
                                        "drivers": [
                                        {
                                            "day": "2023-08-01",
                                            "shifts": [{"employeeId": "0000", "startTime":  "11:00:00"}]
                                        }],
                                        "kitchen": [{
                                            "day": "2023-08-01",
                                            "shifts": [{"employeeId": "0000", "startTime":  "11:00:00"}]
                                        }],
                                        "wishes": []
                                    }
                                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedSchedule));
        mock.close();
        mock2.close();
    }

    @Test
    void saveWorkSchedule_withExistingSchedule() throws Exception {
        MockedStatic<LocalTime> mock = mockStatic(LocalTime.class);
        var time = LocalTime.of(11, 0);
        mock.when(() -> LocalTime.of(11, 0)).thenReturn(time);
        MockedStatic<LocalDate> mock2 = mockStatic(LocalDate.class);
        var day1 = LocalDate.of(2023,8,1);
        mock2.when(() -> LocalDate.of(2023,8,1)).thenReturn(day1);

        WorkSchedule expected = new WorkSchedule("SomeId", 30,
                new ArrayList<>(List.of(
                        new ShiftSchedule(LocalDate.of(2023,8,1), List.of(
                                new WorkShift("0000", LocalTime.of(11, 0))
                        )))),
                new ArrayList<>(List.of(new ShiftSchedule(LocalDate.of(2023,8,1), List.of(
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
                                        "name": 30,
                                        "drivers": [
                                        {
                                            "day": "2023-08-01",
                                            "shifts": [{"employeeId": "0000", "startTime":  "11:00:00"}]
                                        }],
                                        "kitchen": [{
                                            "day": "2023-08-01",
                                            "shifts": [{"employeeId": "0000", "startTime":  "11:00:00"}]
                                        }],
                                        "wishes": []
                                    }
                                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedSchedule));
        mock.close();
        mock2.close();
    }

    @Test
    void getEmployeeWish_whenWishesExist() throws Exception {
        MockedStatic<LocalTime> mock = mockStatic(LocalTime.class);
        var time = LocalTime.of(11, 0);
        mock.when(() -> LocalTime.of(11, 0)).thenReturn(time);
        MockedStatic<LocalDate> mock2 = mockStatic(LocalDate.class);
        var day1 = LocalDate.of(2023,8,1);
        var day2 = LocalDate.of(2023,8,4);
        mock2.when(() -> LocalDate.of(2023,8,1)).thenReturn(day1);
        mock2.when(() -> LocalDate.of(2023,8,4)).thenReturn(day2);

        WorkSchedule workSchedule = new WorkSchedule("SomeID", 31,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(List.of(
                        new WishSchedule("0000", new ArrayList<>(
                                List.of(
                                        new Shifts(LocalDate.of(2023,8,1), LocalTime.of(11, 0)),
                                        new Shifts(LocalDate.of(2023,8,4), LocalTime.of(11, 0))
                                ))))));
        List<Shifts> expectedList = new ArrayList<>(List.of(
                new Shifts(LocalDate.of(2023,8,1), LocalTime.of(11, 0)),
                new Shifts(LocalDate.of(2023,8,4), LocalTime.of(11, 0))
        ));
        scheduleService.addWorkSchedule(workSchedule);

        String expected = objectMapper.writeValueAsString(expectedList);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/schedule/0000/31/wish"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
        mock.close();
        mock2.close();
    }

    @Test
    void getEmployeeWish_whenNoWishesExist() throws Exception {
        WorkSchedule workSchedule = new WorkSchedule("SomeID", 99,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>());
        List<Shifts> expectedList = new ArrayList<>();
        scheduleService.addWorkSchedule(workSchedule);

        String expected = objectMapper.writeValueAsString(expectedList);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/schedule/0000/99/wish"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }

    @Test
    void getEmployeeShifts_whenShiftsExist() throws Exception {
        MockedStatic<LocalTime> mock = mockStatic(LocalTime.class);
        var time = LocalTime.of(11, 0);
        mock.when(() -> LocalTime.of(11, 0)).thenReturn(time);
        MockedStatic<LocalDate> mock2 = mockStatic(LocalDate.class);
        var day1 = LocalDate.of(2023,8,1);
        mock2.when(() -> LocalDate.of(2023,8,1)).thenReturn(day1);

        WorkSchedule workSchedule = new WorkSchedule("SomeID", 30,
                new ArrayList<>(List.of(
                        new ShiftSchedule(LocalDate.of(2023,8,1), new ArrayList<>(
                                List.of(
                                        new WorkShift("0000", LocalTime.of(11, 0))
                                ))))),
                new ArrayList<>(),
                new ArrayList<>());

        List<Shifts> expectedList = new ArrayList<>(List.of(
                new Shifts(LocalDate.of(2023,8,1), LocalTime.of(11, 0))
        ));
        scheduleService.addWorkSchedule(workSchedule);

        String expected = objectMapper.writeValueAsString(expectedList);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/schedule/0000/30"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
        mock.close();
        mock2.close();
    }

    @Test
    void saveEmployeeWish() throws Exception {
        MockedStatic<LocalTime> mock = mockStatic(LocalTime.class);
        var time = LocalTime.of(11, 0);
        mock.when(() -> LocalTime.of(11, 0)).thenReturn(time);

        MockedStatic<LocalDate> mock2 = mockStatic(LocalDate.class);
        var day1 = LocalDate.of(2023,8,1);
        var day2 = LocalDate.of(2023,8,2);
        var day3 = LocalDate.of(2023,8,3);
        mock2.when(() -> LocalDate.of(2023,8,1)).thenReturn(day1);
        mock2.when(() -> LocalDate.of(2023,8,2)).thenReturn(day2);
        mock2.when(() -> LocalDate.of(2023,8,3)).thenReturn(day3);

        WorkSchedule workSchedule = new WorkSchedule("SomeID", 31,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(List.of(
                        new WishSchedule("0000", new ArrayList<>(
                                List.of(
                                        new Shifts(LocalDate.of(2023,8,1), LocalTime.of(11, 0)),
                                        new Shifts(LocalDate.of(2023,8,3), LocalTime.of(11, 0))
                                ))))));

        List<Shifts> expectedList = new ArrayList<>(List.of(
                new Shifts(LocalDate.of(2023,8,1), LocalTime.of(11, 0)),
                new Shifts(LocalDate.of(2023,8,2), LocalTime.of(11, 0))

        ));
        scheduleService.addWorkSchedule(workSchedule);

        String expected = objectMapper.writeValueAsString(expectedList);


        mockMvc.perform(MockMvcRequestBuilders.put("/api/schedule/0000/31")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            [
                                {
                                    "day": "2023-08-01",
                                    "startTime": "11:00:00"
                                },
                                {
                                    "day": "2023-08-02",
                                    "startTime": "11:00:00"
                                }
                            ]
                        """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
        mock.close();
        mock2.close();
    }
}

package com.example.backend.service;

import com.example.backend.model.schedule.ShiftSchedule;
import com.example.backend.model.schedule.WorkSchedule;
import com.example.backend.model.schedule.WorkScheduleNoId;
import com.example.backend.model.shift.WorkShift;
import com.example.backend.repository.EmployeeRepo;
import com.example.backend.repository.ScheduleRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class ScheduleServiceTest {
    ScheduleRepo scheduleRepo = mock(ScheduleRepo.class);
    ScheduleService scheduleService = new ScheduleService(scheduleRepo);

    @Test
    void getWorkSchedule_whenAddNewSchedule() {
        //Given
        WorkSchedule newWorkSchedule = new WorkSchedule("SomeId", "SomeName", new ArrayList<>(), new ArrayList<>());

        //When
        WorkSchedule actualWorkSchedule = scheduleService.addWorkSchedule(newWorkSchedule);
        //Then
        Assertions.assertEquals(newWorkSchedule, actualWorkSchedule);
    }

    @Test
    void getSchedule_whenGetWorkSchedule() {
        //Given
        WorkScheduleNoId expected = new WorkScheduleNoId();
        expected.setName("SomeName");
        expected.setDrivers(new ArrayList<>(List.of(
                new ShiftSchedule("MONDAY", List.of(
                        new WorkShift("0000", 1100))))));
        expected.setKitchen(new ArrayList<>(List.of(
                new ShiftSchedule("MONDAY", List.of(
                        new WorkShift("0000", 1100))))));
        WorkSchedule workSchedule = new WorkSchedule("SomeId", "SomeName",
                new ArrayList<>(List.of(
                        new ShiftSchedule("MONDAY", List.of(
                                new WorkShift("0000", 1100))))),
                new ArrayList<>(List.of(
                        new ShiftSchedule("MONDAY", List.of(
                                new WorkShift("0000", 1100))))));
        WorkSchedule workSchedule2 = new WorkSchedule("SomeOtherId", "SomeOtherName",
                new ArrayList<>(List.of(
                        new ShiftSchedule("MONDAY", List.of(
                                new WorkShift("0000", 1100))))),
                new ArrayList<>(List.of(
                        new ShiftSchedule("MONDAY", List.of(
                                new WorkShift("0000", 1100))))));
        List<WorkSchedule> workScheduleList = new ArrayList<>(List.of(workSchedule, workSchedule2));
        //When
        when(scheduleRepo.findAll()).thenReturn(workScheduleList);
        WorkScheduleNoId actualWorkSchedule = scheduleService.getWorkSchedule("SomeName");
        //Then
        verify(scheduleRepo).findAll();
        Assertions.assertEquals(expected, actualWorkSchedule);
    }
    @Test
    void getDefaultSchedule_whenUnknowendName(){
        WorkScheduleNoId expected = new WorkScheduleNoId();
        expected.setName("defaultSchedule");
        expected.setDrivers(new ArrayList<>(List.of(
                new ShiftSchedule("MONDAY", List.of(
                        new WorkShift("0000", 1100))))));
        expected.setKitchen(new ArrayList<>(List.of(
                new ShiftSchedule("MONDAY", List.of(
                        new WorkShift("0000", 1100))))));

        WorkSchedule defaultSchedule = new WorkSchedule("1234567890", "defaultSchedule",
                new ArrayList<>(List.of(
                        new ShiftSchedule("MONDAY", List.of(
                                new WorkShift("0000", 1100))))),
                new ArrayList<>(List.of(
                        new ShiftSchedule("MONDAY", List.of(
                                new WorkShift("0000", 1100))))));

        //When
        when(scheduleRepo.findAll()).thenReturn(List.of());
        when(scheduleRepo.findById("1234567890")).thenReturn(Optional.of(defaultSchedule));
        //Then
        WorkScheduleNoId actual = scheduleService.getWorkSchedule("WrongName");
        verify(scheduleRepo).findAll();
        verify(scheduleRepo).findById("1234567890");
        Assertions.assertEquals(expected, actual);
    }
}

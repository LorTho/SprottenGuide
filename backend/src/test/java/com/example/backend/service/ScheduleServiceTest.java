package com.example.backend.service;

import com.example.backend.model.*;
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
    EmployeeRepo employeeRepo = mock(EmployeeRepo.class);
    EmployeeService employeeService = new EmployeeService(employeeRepo);
    ScheduleService scheduleService = new ScheduleService(scheduleRepo, employeeService);

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
        WorkScheduleExport expected = new WorkScheduleExport();
        expected.setName("SomeName");
        expected.setDrivers(new ArrayList<>(List.of(
                new ShiftSchedule("MONDAY", List.of(
                        new WorkShift("Test", 1100))))));
        expected.setKitchen(new ArrayList<>(List.of(
                new ShiftSchedule("MONDAY", List.of(
                        new WorkShift("Test", 1100))))));
        WorkSchedule workSchedule = new WorkSchedule("SomeId", "SomeName",
                new ArrayList<>(List.of(
                        new ShiftSchedule("MONDAY", List.of(
                                new WorkShift("0000", 1100))))),
                new ArrayList<>(List.of(
                        new ShiftSchedule("MONDAY", List.of(
                                new WorkShift("0000", 1100))))));
        List<WorkSchedule> workScheduleList = new ArrayList<>(List.of(workSchedule));
        Employee employee = new Employee("0000", "Test", "Test", new ArrayList<>(), new ArrayList<>());
        //When
        when(scheduleRepo.findAll()).thenReturn(workScheduleList);
        when(employeeRepo.findById("0000")).thenReturn(Optional.of(employee));
        WorkScheduleExport actualWorkSchedule = scheduleService.getWorkSchedule("SomeName");
        //Then
        verify(scheduleRepo).findAll();
        Assertions.assertEquals(expected, actualWorkSchedule);
    }

    @Test
    void getNamesFromID() {
        Employee employee = new Employee("0000", "Test", "Test", new ArrayList<>(), new ArrayList<>());
        ShiftSchedule shifts = new ShiftSchedule("MONDAY", List.of(
                new WorkShift("0000", 1100)));
        ShiftSchedule expected = new ShiftSchedule("MONDAY", List.of(
                new WorkShift("Test", 1100)));

        when(employeeRepo.findById("0000")).thenReturn(Optional.of(employee));

        Assertions.assertEquals(expected, scheduleService.getNamesByIdFromEmployeeDB(shifts));

    }
}
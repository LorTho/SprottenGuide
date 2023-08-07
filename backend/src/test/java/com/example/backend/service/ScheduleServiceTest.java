package com.example.backend.service;

import com.example.backend.model.schedule.ShiftSchedule;
import com.example.backend.entities.WorkSchedule;
import com.example.backend.model.schedule.WorkScheduleNoId;
import com.example.backend.model.shift.Shifts;
import com.example.backend.model.shift.WorkShift;
import com.example.backend.repository.ScheduleRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.*;

class ScheduleServiceTest {
    ScheduleRepo scheduleRepo = mock(ScheduleRepo.class);
    ScheduleService scheduleService = new ScheduleService(scheduleRepo);

    @Test
    void getWorkSchedule_whenAddNewSchedule() {
        //Given
        WorkSchedule newWorkSchedule = new WorkSchedule("SomeId", "SomeName", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        //When
        WorkSchedule actualWorkSchedule = scheduleService.addWorkSchedule(newWorkSchedule);
        //Then
        Assertions.assertEquals(newWorkSchedule, actualWorkSchedule);
    }
    @Test
    void saveWorkSchedule_whenAddNewSchedule() {
        //Given
        WorkSchedule newWorkSchedule = new WorkSchedule("SomeId", "SomeName", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        WorkSchedule existingSchedule = new WorkSchedule("SomeOtherId", "SomeName", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        List<WorkSchedule> existingSchedules = new ArrayList<>();
        existingSchedules.add(existingSchedule);
        //When
        when(scheduleRepo.findAll()).thenReturn(existingSchedules);
        WorkSchedule actualWorkSchedule = scheduleService.addWorkSchedule(newWorkSchedule);
        //Then
        Assertions.assertEquals(existingSchedule, actualWorkSchedule);
    }

    @Test
    void getSchedule_whenGetWorkSchedule() {
        //Given
        WorkScheduleNoId expected = new WorkScheduleNoId();
        expected.setName("SomeName");
        expected.setDrivers(new ArrayList<>(List.of(
                new ShiftSchedule("MONDAY", List.of(
                        new WorkShift("0000", LocalTime.of(11,0)))))));
        expected.setKitchen(new ArrayList<>(List.of(
                new ShiftSchedule("MONDAY", List.of(
                        new WorkShift("0000", LocalTime.of(11,0)))))));
        WorkSchedule workSchedule = new WorkSchedule("SomeId", "SomeName",
                new ArrayList<>(List.of(
                        new ShiftSchedule("MONDAY", List.of(
                                new WorkShift("0000", LocalTime.of(11,0)))))),
                new ArrayList<>(List.of(
                        new ShiftSchedule("MONDAY", List.of(
                                new WorkShift("0000", LocalTime.of(11,0)))))),
                new ArrayList<>());
        WorkSchedule workSchedule2 = new WorkSchedule("SomeOtherId", "SomeOtherName",
                new ArrayList<>(List.of(
                        new ShiftSchedule("MONDAY", List.of(
                                new WorkShift("0000", LocalTime.of(11,0)))))),
                new ArrayList<>(List.of(
                        new ShiftSchedule("MONDAY", List.of(
                                new WorkShift("0000", LocalTime.of(11,0)))))),
                new ArrayList<>());
        List<WorkSchedule> workScheduleList = new ArrayList<>(List.of(workSchedule, workSchedule2));
        //When
        when(scheduleRepo.findAll()).thenReturn(workScheduleList);
        WorkScheduleNoId actualWorkSchedule = scheduleService.getWorkSchedule("SomeName");
        //Then
        verify(scheduleRepo).findAll();
        Assertions.assertEquals(expected, actualWorkSchedule);
    }
    @Test
    void getDefaultSchedule_whenUnknownName(){
        WorkScheduleNoId expected = new WorkScheduleNoId();
        expected.setName("defaultSchedule");
        expected.setDrivers(new ArrayList<>(List.of(
                new ShiftSchedule("MONDAY", List.of(
                        new WorkShift("0000", LocalTime.of(11,0)))))));
        expected.setKitchen(new ArrayList<>(List.of(
                new ShiftSchedule("MONDAY", List.of(
                        new WorkShift("0000", LocalTime.of(11,0)))))));

        WorkSchedule defaultSchedule = new WorkSchedule("1234567890", "defaultSchedule",
                new ArrayList<>(List.of(
                        new ShiftSchedule("MONDAY", List.of(
                                new WorkShift("0000", LocalTime.of(11,0)))))),
                new ArrayList<>(List.of(
                        new ShiftSchedule("MONDAY", List.of(
                                new WorkShift("0000", LocalTime.of(11,0)))))),
                new ArrayList<>());

        //When
        when(scheduleRepo.findAll()).thenReturn(List.of());
        when(scheduleRepo.findById("1234567890")).thenReturn(Optional.of(defaultSchedule));
        //Then
        WorkScheduleNoId actual = scheduleService.getWorkSchedule("WrongName");
        verify(scheduleRepo).findAll();
        verify(scheduleRepo).findById("1234567890");
        Assertions.assertEquals(expected, actual);
    }
    @Test
    void getException_whenUnknownNameAndDefaultNotFound(){
        Assertions.assertThrows(NoSuchElementException.class, () -> scheduleService.getWorkSchedule("WrongName"));
    }

    @Test
    void getEmployeeShifts() {
        WorkSchedule defaultSchedule = new WorkSchedule("ID", "defaultSchedule",
                new ArrayList<>(List.of(
                        new ShiftSchedule("MONDAY", List.of(
                                new WorkShift("0000", LocalTime.of(11,0)),
                                new WorkShift("1234", LocalTime.of(17,0)))),
                        new ShiftSchedule("WEDNESDAY", List.of(
                                new WorkShift("0000", LocalTime.of(11,0)),
                                new WorkShift("1234", LocalTime.of(17,0)))))),
                new ArrayList<>(List.of(
                        new ShiftSchedule("FRIDAY", List.of(
                                new WorkShift("0000", LocalTime.of(11,0)))))),
                new ArrayList<>());

        List<Shifts> expectedList = new ArrayList<>(List.of(
                new Shifts("MONDAY", LocalTime.of(11,0)),
                new Shifts("WEDNESDAY", LocalTime.of(11,0)),
                new Shifts("FRIDAY", LocalTime.of(11,0))
        ));

        //When
        when(scheduleRepo.findById("ID")).thenReturn(Optional.of(defaultSchedule));
        List<Shifts> actual = scheduleService.getEmployeeShifts("0000", "ID");

        verify(scheduleRepo).findById("ID");
        Assertions.assertEquals(expectedList, actual);
    }
}

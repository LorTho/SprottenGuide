package com.example.backend.service;

import com.example.backend.model.schedule.ShiftSchedule;
import com.example.backend.entities.WorkSchedule;
import com.example.backend.model.schedule.WishSchedule;
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
        String name = "SomeName";
        WorkSchedule workSchedule = new WorkSchedule("SomeId", name, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        //When
        when(scheduleRepo.findByName(name)).thenReturn(Optional.of(workSchedule));
        WorkSchedule actualWorkSchedule = scheduleService.getWorkSchedule(name);
        //Then
        verify(scheduleRepo).findByName(name);
        Assertions.assertEquals(workSchedule, actualWorkSchedule);
    }

    @Test
    void getDefaultSchedule_whenUnknownName() {
        WorkSchedule expected = new WorkSchedule("1234567890", "defaultSchedule",
                new ArrayList<>(List.of(
                        new ShiftSchedule("MONDAY", List.of(
                                new WorkShift("0000", LocalTime.of(11, 0)))))),
                new ArrayList<>(List.of(
                        new ShiftSchedule("MONDAY", List.of(
                                new WorkShift("0000", LocalTime.of(11, 0)))))),
                new ArrayList<>());

        WorkSchedule defaultSchedule = new WorkSchedule("1234567890", "defaultSchedule",
                new ArrayList<>(List.of(
                        new ShiftSchedule("MONDAY", List.of(
                                new WorkShift("0000", LocalTime.of(11, 0)))))),
                new ArrayList<>(List.of(
                        new ShiftSchedule("MONDAY", List.of(
                                new WorkShift("0000", LocalTime.of(11, 0)))))),
                new ArrayList<>());

        //When
        when(scheduleRepo.findByName("WrongName")).thenReturn(Optional.empty());
        when(scheduleRepo.findByName("defaultSchedule")).thenReturn(Optional.of(defaultSchedule));
        //Then
        WorkSchedule actual = scheduleService.getWorkSchedule("WrongName");
        verify(scheduleRepo).findByName("WrongName");
        verify(scheduleRepo).findByName("defaultSchedule");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getException_whenUnknownNameAndDefaultNotFound() {
        Assertions.assertThrows(NoSuchElementException.class, () -> scheduleService.getWorkSchedule("WrongName"));
    }

    @Test
    void getEmployeeShifts() {
        WorkSchedule defaultSchedule = new WorkSchedule("ID", "defaultSchedule",
                new ArrayList<>(List.of(
                        new ShiftSchedule("MONDAY", List.of(
                                new WorkShift("0000", LocalTime.of(11, 0)),
                                new WorkShift("1234", LocalTime.of(17, 0)))),
                        new ShiftSchedule("WEDNESDAY", List.of(
                                new WorkShift("0000", LocalTime.of(11, 0)),
                                new WorkShift("1234", LocalTime.of(17, 0)))))),
                new ArrayList<>(List.of(
                        new ShiftSchedule("FRIDAY", List.of(
                                new WorkShift("0000", LocalTime.of(11, 0)))))),
                new ArrayList<>());

        List<Shifts> expectedList = new ArrayList<>(List.of(
                new Shifts("MONDAY", LocalTime.of(11, 0)),
                new Shifts("WEDNESDAY", LocalTime.of(11, 0)),
                new Shifts("FRIDAY", LocalTime.of(11, 0))
        ));

        //When
        when(scheduleRepo.findByName("defaultSchedule")).thenReturn(Optional.of(defaultSchedule));
        List<Shifts> actual = scheduleService.getEmployeeShifts("0000", "defaultSchedule");

        verify(scheduleRepo).findByName("defaultSchedule");
        Assertions.assertEquals(expectedList, actual);
    }

    @Test
    void getEmployeeWishes_whenWishesExist() {
        WorkSchedule defaultSchedule = new WorkSchedule("ID", "defaultSchedule",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(List.of(
                        new WishSchedule("0000", List.of(
                                new Shifts("MONDAY", LocalTime.of(11,0)),
                                new Shifts("FRIDAY", LocalTime.of(11,0))
                )))));

        List<Shifts> expectedList = new ArrayList<>(List.of(
                new Shifts("MONDAY", LocalTime.of(11, 0)),
                new Shifts("FRIDAY", LocalTime.of(11, 0))
        ));

        //When
        when(scheduleRepo.findByName("defaultSchedule")).thenReturn(Optional.of(defaultSchedule));
        List<Shifts> actual = scheduleService.getEmployeeWishes("0000", "defaultSchedule");

        verify(scheduleRepo).findByName("defaultSchedule");
        Assertions.assertEquals(expectedList, actual);
    }
    @Test
    void getEmployeeWishes_whenNoWishesExist() {
        WorkSchedule defaultSchedule = new WorkSchedule("ID", "defaultSchedule",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(List.of(
                        new WishSchedule("1111", List.of(
                                new Shifts("MONDAY", LocalTime.of(11,0)),
                                new Shifts("FRIDAY", LocalTime.of(11,0))
                        )))));

        List<Shifts> expectedList = new ArrayList<>();

        //When
        when(scheduleRepo.findByName("defaultSchedule")).thenReturn(Optional.of(defaultSchedule));
        List<Shifts> actual = scheduleService.getEmployeeWishes("0000", "defaultSchedule");

        verify(scheduleRepo).findByName("defaultSchedule");
        Assertions.assertEquals(expectedList, actual);
    }
    @Test
    void getException_whenUnknownSchedule() {
        Assertions.assertThrows(NoSuchElementException.class, () -> scheduleService.getEmployeeWishes("0000", "WrongName"));
    }
    @Test
    void saveEmployeeWishes_whenWishesExist() {
        WorkSchedule defaultSchedule = new WorkSchedule("ID", "defaultSchedule",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(List.of(
                        new WishSchedule("0000", List.of(
                                new Shifts("MONDAY", LocalTime.of(11,0)),
                                new Shifts("TUESDAY", LocalTime.of(11,0))
                        )))));

        List<Shifts> expectedList = new ArrayList<>(List.of(
                new Shifts("MONDAY", LocalTime.of(11, 0)),
                new Shifts("FRIDAY", LocalTime.of(11, 0))
        ));

        //When
        when(scheduleRepo.findByName("defaultSchedule")).thenReturn(Optional.of(defaultSchedule));
        List<Shifts> actual = scheduleService.saveEmployeeWishes("0000", "defaultSchedule", List.of(
                new Shifts("MONDAY", LocalTime.of(11, 0)),
                new Shifts("FRIDAY", LocalTime.of(11, 0))
        ));

        verify(scheduleRepo).findByName("defaultSchedule");
        Assertions.assertEquals(expectedList, actual);
    }
    @Test
    void saveEmployeeWishes_whenNoWishesExist() {
        WorkSchedule defaultSchedule = new WorkSchedule("ID", "defaultSchedule",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(List.of(
                        new WishSchedule("1111", List.of(
                                new Shifts("MONDAY", LocalTime.of(11,0)),
                                new Shifts("FRIDAY", LocalTime.of(11,0))
                        )))));

        List<Shifts> expectedList = new ArrayList<>(List.of(
                new Shifts("MONDAY", LocalTime.of(11, 0)),
                new Shifts("FRIDAY", LocalTime.of(11, 0))
        ));

        //When
        when(scheduleRepo.findByName("defaultSchedule")).thenReturn(Optional.of(defaultSchedule));
        List<Shifts> actual = scheduleService.saveEmployeeWishes("0000", "defaultSchedule", List.of(
                new Shifts("MONDAY", LocalTime.of(11, 0)),
                new Shifts("FRIDAY", LocalTime.of(11, 0))
        ));

        verify(scheduleRepo).findByName("defaultSchedule");
        Assertions.assertEquals(expectedList, actual);
    }
    @Test
    void getException_whenUnknownSaveSchedule() {
        Assertions.assertThrows(NoSuchElementException.class, () -> scheduleService.saveEmployeeWishes("0000", "WrongName", null));
    }
}

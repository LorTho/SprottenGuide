package com.example.backend.service;

import com.example.backend.model.schedule.ShiftSchedule;
import com.example.backend.entities.WorkSchedule;
import com.example.backend.model.schedule.WishSchedule;
import com.example.backend.model.schedule.WorkScheduleNoId;
import com.example.backend.model.shift.Shifts;
import com.example.backend.model.shift.ShiftsWithDayString;
import com.example.backend.model.shift.WorkShift;
import com.example.backend.repository.ScheduleRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class ScheduleServiceTest {
    ScheduleRepo scheduleRepo = mock(ScheduleRepo.class);
    ScheduleService scheduleService = new ScheduleService(scheduleRepo);

    @Test
    @DirtiesContext
    void getWorkSchedule_whenAddNewSchedule() {
        //Given
        WorkSchedule newWorkSchedule = new WorkSchedule("SomeId", 30, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        //When
        WorkSchedule actualWorkSchedule = scheduleService.addWorkSchedule(newWorkSchedule);
        //Then
        Assertions.assertEquals(newWorkSchedule, actualWorkSchedule);
    }

    @Test
    @DirtiesContext
    void saveWorkSchedule_whenNewScheduleIsNew(){
        WorkSchedule newWorkSchedule = new WorkSchedule("SomeId", 30, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        WorkSchedule existingSchedule = new WorkSchedule("SomeOtherId", 29, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        //When
        when(scheduleRepo.findAll()).thenReturn(List.of(existingSchedule));
        WorkSchedule actualWorkSchedule = scheduleService.addWorkSchedule(newWorkSchedule);
        //Then
        verify(scheduleRepo).findAll();
        Assertions.assertEquals(newWorkSchedule, actualWorkSchedule);
    }
    @Test
    @DirtiesContext
    void saveWorkSchedule_whenNewScheduleExists() {
        //Given
        WorkSchedule newWorkSchedule = new WorkSchedule("SomeId", 30, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        WorkSchedule existingSchedule = new WorkSchedule("SomeOtherId", 30, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        List<WorkSchedule> existingSchedules = new ArrayList<>();
        existingSchedules.add(existingSchedule);
        //When
        when(scheduleRepo.findAll()).thenReturn(existingSchedules);
        WorkSchedule actualWorkSchedule = scheduleService.addWorkSchedule(newWorkSchedule);
        //Then
        Assertions.assertEquals(existingSchedule, actualWorkSchedule);
    }

    @Test
    @DirtiesContext
    void getSchedule_whenGetWorkSchedule() {
        //Given
        int name = 30;
        WorkSchedule workSchedule = new WorkSchedule("SomeId", name, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        //When
        when(scheduleRepo.findByName(name)).thenReturn(Optional.of(workSchedule));
        WorkSchedule actualWorkSchedule = scheduleService.getWorkSchedule(name);
        //Then
        verify(scheduleRepo).findByName(name);
        Assertions.assertEquals(workSchedule, actualWorkSchedule);
    }

    @Test
    @DirtiesContext
    void getDefaultSchedule_whenUnknownName() {
        WorkScheduleNoId expected = new WorkScheduleNoId();
        expected.setName(32);
        expected.setDrivers(
                new ArrayList<>(List.of(
                        new ShiftSchedule(LocalDate.of(2023,8,7), List.of(
                                new WorkShift(null, LocalTime.of(11, 0)))))));
        expected.setKitchen(
                new ArrayList<>(List.of(
                        new ShiftSchedule(LocalDate.of(2023,8,7), List.of(
                                new WorkShift(null, LocalTime.of(11, 0)))))));
        expected.setWishes(new ArrayList<>());

        WorkSchedule defaultSchedule = new WorkSchedule("1234567890", 0,
                new ArrayList<>(List.of(
                        new ShiftSchedule(LocalDate.of(2023,8,1), List.of(
                                new WorkShift(null, LocalTime.of(11, 0)))))),
                new ArrayList<>(List.of(
                        new ShiftSchedule(LocalDate.of(2023,8,1), List.of(
                                new WorkShift(null, LocalTime.of(11, 0)))))),
                new ArrayList<>());

        //When
        when(scheduleRepo.findByName(32)).thenReturn(Optional.empty());
        when(scheduleRepo.findByName(0)).thenReturn(Optional.of(defaultSchedule));
        //Then
        WorkSchedule actual = scheduleService.getWorkSchedule(32);
        WorkScheduleNoId actualNoId = new WorkScheduleNoId(actual.getName(), actual.getDrivers(), actual.getKitchen(), actual.getWishes());
        verify(scheduleRepo).findByName(32);
        verify(scheduleRepo).findByName(0);
        Assertions.assertEquals(expected, actualNoId);
    }

    @Test
    @DirtiesContext
    void getException_whenUnknownNameAndDefaultNotFound() {
        Assertions.assertThrows(RuntimeException.class, () -> scheduleService.getWorkSchedule(99));
    }

    @Test
    @DirtiesContext
    void getEmployeeShifts() {
        WorkSchedule defaultSchedule = new WorkSchedule("ID", 0,
                new ArrayList<>(List.of(
                        new ShiftSchedule(LocalDate.of(2023,8,1), List.of(
                                new WorkShift("0000", LocalTime.of(11, 0)),
                                new WorkShift("1234", LocalTime.of(17, 0)))),
                        new ShiftSchedule(LocalDate.of(2023,8,3), List.of(
                                new WorkShift("0000", LocalTime.of(11, 0)),
                                new WorkShift("1234", LocalTime.of(17, 0)))))),
                new ArrayList<>(List.of(
                        new ShiftSchedule(LocalDate.of(2023,8,5), List.of(
                                new WorkShift("0000", LocalTime.of(11, 0)),
                                new WorkShift("1234", LocalTime.of(17, 0)))))),
                new ArrayList<>());

        List<Shifts> expectedList = new ArrayList<>(List.of(
                new Shifts(LocalDate.of(2023,8,1), LocalTime.of(11, 0)),
                new Shifts(LocalDate.of(2023,8,3), LocalTime.of(11, 0)),
                new Shifts(LocalDate.of(2023,8,5), LocalTime.of(11, 0))
        ));

        //When
        when(scheduleRepo.findByName(0)).thenReturn(Optional.of(defaultSchedule));
        List<Shifts> actual = scheduleService.getEmployeeShifts("0000", 0);

        verify(scheduleRepo).findByName(0);
        Assertions.assertEquals(expectedList, actual);
    }

    @Test
    @DirtiesContext
    void getEmployeeWishes_whenWishesExist() {
        WorkSchedule defaultSchedule = new WorkSchedule("ID", 33,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(List.of(
                        new WishSchedule("0000", List.of(
                                new Shifts(LocalDate.of(2023,8,15), LocalTime.of(11, 0)),
                                new Shifts(LocalDate.of(2023,8,19), LocalTime.of(11, 0))
                        )))));

        List<ShiftsWithDayString> expectedList = new ArrayList<>(List.of(
                new ShiftsWithDayString("TUESDAY", LocalTime.of(11, 0)),
                new ShiftsWithDayString("SATURDAY", LocalTime.of(11, 0))
        ));

        //When
        when(scheduleRepo.findByName(33)).thenReturn(Optional.of(defaultSchedule));
        List<ShiftsWithDayString> actual = scheduleService.getEmployeeWishes("0000", 33);

        verify(scheduleRepo).findByName(33);
        Assertions.assertEquals(expectedList, actual);
    }

    @Test
    @DirtiesContext
    void getEmployeeWishes_whenNoWishesExist() {
        WorkSchedule defaultSchedule = new WorkSchedule("ID", 0,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(List.of(
                        new WishSchedule("1111", List.of(
                                new Shifts(LocalDate.of(2023,8,1), LocalTime.of(11, 0)),
                                new Shifts(LocalDate.of(2023,8,5), LocalTime.of(11, 0))
                        )))));

        List<ShiftsWithDayString> expectedList = new ArrayList<>();

        //When
        when(scheduleRepo.findByName(0)).thenReturn(Optional.of(defaultSchedule));
        List<ShiftsWithDayString> actual = scheduleService.getEmployeeWishes("0000", 0);

        verify(scheduleRepo).findByName(0);
        Assertions.assertEquals(expectedList, actual);
    }

    @Test
    @DirtiesContext
    void getException_whenUnknownSchedule() {
        Assertions.assertThrows(RuntimeException.class, () -> scheduleService.getEmployeeWishes("0000", 99));
    }

    @Test
    @DirtiesContext
    void saveEmployeeWishes_whenWishesExist() {
        WorkSchedule defaultSchedule = new WorkSchedule("ID", 33,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(List.of(
                        new WishSchedule("0000", List.of(
                                new Shifts(LocalDate.of(2023,8,14), LocalTime.of(11, 0)),
                                new Shifts(LocalDate.of(2023,8,16), LocalTime.of(11, 0))
                        )))));

        List<Shifts> expectedList = new ArrayList<>(List.of(
                new Shifts(LocalDate.of(2023,8,14), LocalTime.of(11, 0)),
                new Shifts(LocalDate.of(2023,8,15), LocalTime.of(11, 0))
        ));

        //When
        when(scheduleRepo.findByName(33)).thenReturn(Optional.of(defaultSchedule));
        List<Shifts> actual = scheduleService.saveEmployeeWishes("0000", 33, List.of(
                new ShiftsWithDayString("MONDAY", LocalTime.of(11, 0)),
                new ShiftsWithDayString("TUESDAY", LocalTime.of(11, 0))
        ));

        verify(scheduleRepo).findByName(33);
        Assertions.assertEquals(expectedList, actual);
    }

    @Test
    @DirtiesContext
    void saveEmployeeWishes_whenNoWishesExist() {
        WorkSchedule defaultSchedule = new WorkSchedule("ID", 2,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(List.of(
                        new WishSchedule("1111", List.of(
                                new Shifts(LocalDate.of(2023,8,1), LocalTime.of(11, 0)),
                                new Shifts(LocalDate.of(2023,8,5), LocalTime.of(11, 0))
                        )))));

        List<Shifts> expectedList = new ArrayList<>(List.of(
                new Shifts(LocalDate.of(2023,1,9), LocalTime.of(11, 0)),
                new Shifts(LocalDate.of(2023,1,13), LocalTime.of(11, 0))
        ));

        //When
        when(scheduleRepo.findByName(2)).thenReturn(Optional.of(defaultSchedule));
        List<Shifts> actual = scheduleService.saveEmployeeWishes("0000", 2, List.of(
                new ShiftsWithDayString("MONDAY", LocalTime.of(11, 0)),
                new ShiftsWithDayString("FRIDAY", LocalTime.of(11, 0))
        ));

        verify(scheduleRepo).findByName(2);
        Assertions.assertEquals(expectedList, actual);
    }

    @Test
    void getException_whenUnknownSaveSchedule() {
        List<ShiftsWithDayString> list = new ArrayList<>();
        Assertions.assertThrows(RuntimeException.class, () -> scheduleService.saveEmployeeWishes("0000", 99, list));
    }
}

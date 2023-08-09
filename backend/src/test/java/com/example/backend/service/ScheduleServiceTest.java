package com.example.backend.service;

import com.example.backend.model.schedule.ShiftSchedule;
import com.example.backend.entities.WorkSchedule;
import com.example.backend.model.schedule.WishSchedule;
import com.example.backend.model.schedule.WorkScheduleNoId;
import com.example.backend.model.shift.Shifts;
import com.example.backend.model.shift.WorkShift;
import com.example.backend.repository.ScheduleRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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
        WorkSchedule newWorkSchedule = new WorkSchedule("SomeId", 30, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        //When
        WorkSchedule actualWorkSchedule = scheduleService.addWorkSchedule(newWorkSchedule);
        //Then
        Assertions.assertEquals(newWorkSchedule, actualWorkSchedule);
    }

    @Test
    void saveWorkSchedule_whenAddNewSchedule() {
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
    void getDefaultSchedule_whenUnknownName() {
        WorkScheduleNoId expected = new WorkScheduleNoId();
        expected.setName(32);
        expected.setDrivers(
                new ArrayList<>(List.of(
                        new ShiftSchedule(LocalDate.of(2023,8,7), List.of(
                                new WorkShift("0000", LocalTime.of(11, 0)))))));
        expected.setKitchen(
                new ArrayList<>(List.of(
                        new ShiftSchedule(LocalDate.of(2023,8,7), List.of(
                                new WorkShift("0000", LocalTime.of(11, 0)))))));
        expected.setWishes(new ArrayList<>());

        WorkSchedule defaultSchedule = new WorkSchedule("1234567890", 0,
                new ArrayList<>(List.of(
                        new ShiftSchedule(LocalDate.of(2023,8,1), List.of(
                                new WorkShift("0000", LocalTime.of(11, 0)))))),
                new ArrayList<>(List.of(
                        new ShiftSchedule(LocalDate.of(2023,8,1), List.of(
                                new WorkShift("0000", LocalTime.of(11, 0)))))),
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
    void getException_whenUnknownNameAndDefaultNotFound() {
        Assertions.assertThrows(RuntimeException.class, () -> scheduleService.getWorkSchedule(99));
    }

    @Test
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
    void getEmployeeWishes_whenWishesExist() {
        WorkSchedule defaultSchedule = new WorkSchedule("ID", 0,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(List.of(
                        new WishSchedule("0000", List.of(
                                new Shifts(LocalDate.of(2023,8,1), LocalTime.of(11, 0)),
                                new Shifts(LocalDate.of(2023,8,5), LocalTime.of(11, 0))
                        )))));

        List<Shifts> expectedList = new ArrayList<>(List.of(
                new Shifts(LocalDate.of(2023,8,1), LocalTime.of(11, 0)),
                new Shifts(LocalDate.of(2023,8,5), LocalTime.of(11, 0))
        ));

        //When
        when(scheduleRepo.findByName(0)).thenReturn(Optional.of(defaultSchedule));
        List<Shifts> actual = scheduleService.getEmployeeWishes("0000", 0);

        verify(scheduleRepo).findByName(0);
        Assertions.assertEquals(expectedList, actual);
    }

    @Test
    void getEmployeeWishes_whenNoWishesExist() {
        WorkSchedule defaultSchedule = new WorkSchedule("ID", 0,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(List.of(
                        new WishSchedule("1111", List.of(
                                new Shifts(LocalDate.of(2023,8,1), LocalTime.of(11, 0)),
                                new Shifts(LocalDate.of(2023,8,5), LocalTime.of(11, 0))
                        )))));

        List<Shifts> expectedList = new ArrayList<>();

        //When
        when(scheduleRepo.findByName(0)).thenReturn(Optional.of(defaultSchedule));
        List<Shifts> actual = scheduleService.getEmployeeWishes("0000", 0);

        verify(scheduleRepo).findByName(0);
        Assertions.assertEquals(expectedList, actual);
    }

    @Test
    void getException_whenUnknownSchedule() {
        Assertions.assertThrows(NoSuchElementException.class, () -> scheduleService.getEmployeeWishes("0000", 99));
    }

    @Test
    void saveEmployeeWishes_whenWishesExist() {
        WorkSchedule defaultSchedule = new WorkSchedule("ID", 0,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(List.of(
                        new WishSchedule("0000", List.of(
                                new Shifts(LocalDate.of(2023,8,1), LocalTime.of(11, 0)),
                                new Shifts(LocalDate.of(2023,8,2), LocalTime.of(11, 0))
                        )))));

        List<Shifts> expectedList = new ArrayList<>(List.of(
                new Shifts(LocalDate.of(2023,8,1), LocalTime.of(11, 0)),
                new Shifts(LocalDate.of(2023,8,5), LocalTime.of(11, 0))
        ));

        //When
        when(scheduleRepo.findByName(0)).thenReturn(Optional.of(defaultSchedule));
        List<Shifts> actual = scheduleService.saveEmployeeWishes("0000", 0, List.of(
                new Shifts(LocalDate.of(2023,8,1), LocalTime.of(11, 0)),
                new Shifts(LocalDate.of(2023,8,5), LocalTime.of(11, 0))
        ));

        verify(scheduleRepo).findByName(0);
        Assertions.assertEquals(expectedList, actual);
    }

    @Test
    void saveEmployeeWishes_whenNoWishesExist() {
        WorkSchedule defaultSchedule = new WorkSchedule("ID", 0,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(List.of(
                        new WishSchedule("1111", List.of(
                                new Shifts(LocalDate.of(2023,8,1), LocalTime.of(11, 0)),
                                new Shifts(LocalDate.of(2023,8,5), LocalTime.of(11, 0))
                        )))));

        List<Shifts> expectedList = new ArrayList<>(List.of(
                new Shifts(LocalDate.of(2023,8,1), LocalTime.of(11, 0)),
                new Shifts(LocalDate.of(2023,8,5), LocalTime.of(11, 0))
        ));

        //When
        when(scheduleRepo.findByName(0)).thenReturn(Optional.of(defaultSchedule));
        List<Shifts> actual = scheduleService.saveEmployeeWishes("0000", 0, List.of(
                new Shifts(LocalDate.of(2023,8,1), LocalTime.of(11, 0)),
                new Shifts(LocalDate.of(2023,8,5), LocalTime.of(11, 0))
        ));

        verify(scheduleRepo).findByName(0);
        Assertions.assertEquals(expectedList, actual);
    }

    @Test
    void getException_whenUnknownSaveSchedule() {
        Assertions.assertThrows(NoSuchElementException.class, () -> scheduleService.saveEmployeeWishes("0000", 99, null));
    }
}

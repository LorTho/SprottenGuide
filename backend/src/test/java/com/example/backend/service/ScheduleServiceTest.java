package com.example.backend.service;

import com.example.backend.model.WorkSchedule;
import com.example.backend.repository.ScheduleRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class ScheduleServiceTest {
    ScheduleRepo scheduleRepo= mock(ScheduleRepo.class);
    ScheduleService scheduleService = new ScheduleService(scheduleRepo);
    @Test
    void getWorkSchedule_whenAddNewSchedule() {
        //Given
        WorkSchedule newWorkSchedule = new WorkSchedule("SomeId", new ArrayList<>(), new ArrayList<>());

        //When
        WorkSchedule actualWorkSchedule = scheduleService.addWorkSchedule(newWorkSchedule);
        //Then
        Assertions.assertEquals(newWorkSchedule, actualWorkSchedule);
    }

    @Test
    void getListOfSchedule_whenGetAll() {
        //Given
        List<WorkSchedule> allWorkSchedule = new ArrayList<>(List.of(
                new WorkSchedule("SomeId", new ArrayList<>(), new ArrayList<>())
        ));

        //When
        when(scheduleRepo.findAll()).thenReturn(allWorkSchedule);
        List<WorkSchedule> actualWorkSchedules = scheduleService.getWorkSchedule();
        //Then
        verify(scheduleRepo).findAll();
        Assertions.assertEquals(allWorkSchedule, actualWorkSchedules);
    }
}
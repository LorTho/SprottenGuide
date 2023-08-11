package com.example.backend.service;

import com.example.backend.entities.MonthlyPlan;
import com.example.backend.entities.WorkSchedule;
import com.example.backend.model.monthly.Daily;
import com.example.backend.model.monthly.DailyPlan;
import com.example.backend.model.schedule.ShiftSchedule;
import com.example.backend.model.shift.WorkShift;
import com.example.backend.repository.MonthlyRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.mockito.Mockito.*;

class MonthlyServiceTest {
    MonthlyRepo monthlyRepo = mock(MonthlyRepo.class);
    ScheduleService scheduleService = mock(ScheduleService.class);

    MonthlyService monthlyService = new MonthlyService(monthlyRepo, scheduleService);

    @Test
    void getDailyPlan() {
        List<DailyPlan> expected = List.of(
                new DailyPlan("0000", null, null, null),
                new DailyPlan("1234", null, null, null));

        int weekNumber = LocalDate.now().get(WeekFields.of(Locale.getDefault()).weekOfYear());
        WorkSchedule workSchedule = new WorkSchedule("SomeId", weekNumber,
                List.of(
                        new ShiftSchedule(LocalDate.now(), List.of(
                                new WorkShift("0000", LocalTime.of(11, 0))
                        )),
                        new ShiftSchedule(LocalDate.now().minusDays(1), List.of(
                                new WorkShift("0000", LocalTime.of(11, 0))
                        ))
                ),
                List.of(new ShiftSchedule(LocalDate.now(), List.of(
                                new WorkShift("1234", LocalTime.of(11, 0))
                        )),
                        new ShiftSchedule(LocalDate.now().minusDays(1), List.of(
                                new WorkShift("0000", LocalTime.of(11, 0))
                        ))
                ), new ArrayList<>());

        //When
        when(scheduleService.getWorkSchedule(weekNumber)).thenReturn(workSchedule);
        Assertions.assertEquals(expected, monthlyService.getDailyPlan());
    }

    @Test
    void getToday_WhenExists() {
        Daily expected = new Daily(LocalDate.now(), List.of(
                new DailyPlan("0000", null, null, null),
                new DailyPlan("1234", null, null, null),
                new DailyPlan("5678", null, null, null)
        ));
        Month monat = LocalDate.now().getMonth();
        MonthlyPlan month = new MonthlyPlan("someId", monat, List.of(
                new Daily(LocalDate.now(), List.of(
                        new DailyPlan("0000", null, null, null),
                        new DailyPlan("1234", null, null, null),
                        new DailyPlan("5678", null, null, null)
                )),
                new Daily(LocalDate.now().minusDays(1), List.of(
                        new DailyPlan("0000", null, null, null),
                        new DailyPlan("1234", null, null, null),
                        new DailyPlan("5678", null, null, null)
                ))
        ));
        //When
        when(monthlyRepo.findByMonth(monat)).thenReturn(Optional.of(month));
        Daily actual = monthlyService.getToday();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getToday_whenNonDailyExists() {
        Daily expected = new Daily(LocalDate.now(), List.of(
                new DailyPlan("0000", null, null, null),
                new DailyPlan("1234", null, null, null)
        ));
        int weekNumber = LocalDate.now().get(WeekFields.of(Locale.getDefault()).weekOfYear());
        WorkSchedule workSchedule = new WorkSchedule("SomeId", weekNumber,
                List.of(new ShiftSchedule(LocalDate.now(), List.of(
                                new WorkShift("0000", LocalTime.of(11, 0))
                        ))
                ), List.of(new ShiftSchedule(LocalDate.now(), List.of(
                        new WorkShift("1234", LocalTime.of(11, 0))
                ))
        ), new ArrayList<>());
        Month monat = LocalDate.now().getMonth();
        MonthlyPlan month = new MonthlyPlan("someId", monat, List.of(
                new Daily(LocalDate.now().minusDays(1), List.of(
                        new DailyPlan("0000", null, null, null),
                        new DailyPlan("1234", null, null, null),
                        new DailyPlan("5678", null, null, null)
                ))
        ));
        //When
        when(monthlyRepo.findByMonth(monat)).thenReturn(Optional.of(month));
        when(scheduleService.getWorkSchedule(weekNumber)).thenReturn(workSchedule);
        Daily actual = monthlyService.getToday();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getToday_whenNonMonthExists() {
        Daily expected = new Daily(LocalDate.now(), List.of(
                new DailyPlan("0000", null, null, null),
                new DailyPlan("1234", null, null, null)
        ));
        int weekNumber = LocalDate.now().get(WeekFields.of(Locale.getDefault()).weekOfYear());
        WorkSchedule workSchedule = new WorkSchedule("SomeId", weekNumber,
                List.of(new ShiftSchedule(LocalDate.now(), List.of(
                                new WorkShift("0000", LocalTime.of(11, 0))
                        ))
                ), List.of(new ShiftSchedule(LocalDate.now(), List.of(
                        new WorkShift("1234", LocalTime.of(11, 0))
                ))
        ), new ArrayList<>());
        Month monat = LocalDate.now().getMonth();
        //When
        when(monthlyRepo.findByMonth(monat)).thenReturn(Optional.empty());
        when(scheduleService.getWorkSchedule(weekNumber)).thenReturn(workSchedule);
        Daily actual = monthlyService.getToday();
        Assertions.assertEquals(expected, actual);
    }
}
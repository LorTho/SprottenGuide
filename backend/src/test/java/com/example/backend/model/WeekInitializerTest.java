package com.example.backend.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.example.backend.model.WeekInitializer.createWeek;

class WeekInitializerTest {
    
    @Test
    void testReturnedInitalList_whenWeekInitializerIsCalled(){
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        Map<Integer, List<Time>> expectedMap = new HashMap<>();
        expectedMap.put(today.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()), List.of(
                new Time(firstDayOfWeek, LocalTime.MIN),
                new Time(firstDayOfWeek.plusDays(1), LocalTime.MIN),
                new Time(firstDayOfWeek.plusDays(2), LocalTime.MIN),
                new Time(firstDayOfWeek.plusDays(3), LocalTime.MIN),
                new Time(firstDayOfWeek.plusDays(4), LocalTime.MIN),
                new Time(firstDayOfWeek.plusDays(5), LocalTime.MIN),
                new Time(firstDayOfWeek.plusDays(6), LocalTime.MIN)
        ));
        Assertions.assertEquals(expectedMap, createWeek(0));
    }
}
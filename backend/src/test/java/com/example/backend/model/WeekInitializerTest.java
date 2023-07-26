package com.example.backend.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import static com.example.backend.model.WeekInitializer.createWeek;

class WeekInitializerTest {
    
    @Test
    void testReturnedInitalList_whenWeekInitializerIsCalled(){
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        List<Time> expectedList = new ArrayList<>(List.of(
                new Time(firstDayOfWeek, LocalTime.MIN),
                new Time(firstDayOfWeek.plusDays(1), LocalTime.MIN),
                new Time(firstDayOfWeek.plusDays(2), LocalTime.MIN),
                new Time(firstDayOfWeek.plusDays(3), LocalTime.MIN),
                new Time(firstDayOfWeek.plusDays(4), LocalTime.MIN),
                new Time(firstDayOfWeek.plusDays(5), LocalTime.MIN),
                new Time(firstDayOfWeek.plusDays(6), LocalTime.MIN)));
        Assertions.assertEquals(expectedList, createWeek(0));
    }
}
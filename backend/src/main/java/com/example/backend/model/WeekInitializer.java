package com.example.backend.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;

public class WeekInitializer {
    private WeekInitializer() {

    }
    public static Map<Integer, List<Time>> createWeek(int day){
        LocalDate today = LocalDate.now().plusDays(day);
        LocalDate firstDayOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        ArrayList<Time> days = new ArrayList<>();
        Time date;
        for (int i = 0;i<7;i++){
            date = new Time(firstDayOfWeek.plusDays(i), LocalTime.MIN);
            days.add(date);
        }

        Map<Integer, List<Time>> week = new HashMap<>();
        week.put(today.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()), days);
        return week;
    }
}

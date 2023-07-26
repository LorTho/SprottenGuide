package com.example.backend.service;

import com.example.backend.model.Time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class WeekInitializer {
    private WeekInitializer() {

    }
    public static List<Time> createWeek(int day){
        LocalDate today = LocalDate.now().plusDays(day);
        LocalDate firstDayOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        ArrayList<Time> days = new ArrayList<>();
        Time date;
        for (int i = 0;i<7;i++){
            date = new Time(firstDayOfWeek.plusDays(i), LocalTime.MIN);
            days.add(date);
        }

        return days;
    }
}

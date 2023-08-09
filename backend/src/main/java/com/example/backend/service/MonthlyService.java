package com.example.backend.service;

import com.example.backend.entities.MonthlyPlan;
import com.example.backend.model.monthly.Daily;
import com.example.backend.model.monthly.DailyPlan;
import com.example.backend.repository.MonthlyRepo;
import com.example.backend.repository.ScheduleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.spi.ToolProvider.findFirst;

@Service
@RequiredArgsConstructor
public class MonthlyService {
    private final MonthlyRepo monthlyRepo;
    public void getToday(){
        Optional<MonthlyPlan> actualMonth = monthlyRepo.findByName(LocalDate.from(LocalDate.now().getMonth()));
        MonthlyPlan month = new MonthlyPlan();
        if(actualMonth.isEmpty()) {
            monthlyRepo.insert(new MonthlyPlan(IdService.uuid(), Month.from(LocalDate.now().getMonth()), new ArrayList<>()));
            getToday();
        }else{
            month = actualMonth.get();
        }
        List<Daily> dailys = month.getDays();
        Optional<List<DailyPlan>> dailyPlan = Optional.empty();
        for (Daily findDay : dailys) {
            if(findDay.equals(LocalDate.now())){
                dailyPlan = Optional.ofNullable(findDay.getDailyPlanList());
            }
        }
        if(dailyPlan.isEmpty()){

        }

    }
    public static void testLocalTime() {
        System.out.println(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm")));
        System.out.println(LocalTime.now().getHour() + " " + LocalTime.now().getMinute() + " " + LocalTime.now().getSecond());
        System.out.println(LocalDate.now());
        System.out.println(LocalDate.now().getMonth());
        System.out.println(Month.from(LocalDate.now().getMonth()));
    }

    public static void main(String[] args) {
        MonthlyService.testLocalTime();
    }
}



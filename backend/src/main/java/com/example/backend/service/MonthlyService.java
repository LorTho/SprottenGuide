package com.example.backend.service;

import com.example.backend.entities.MonthlyPlan;
import com.example.backend.entities.WorkSchedule;
import com.example.backend.model.monthly.Daily;
import com.example.backend.model.monthly.DailyPlan;
import com.example.backend.model.monthly.Pause;
import com.example.backend.model.schedule.ShiftSchedule;
import com.example.backend.model.shift.WorkShift;
import com.example.backend.repository.MonthlyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.WeekFields;
import java.util.*;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
@RequiredArgsConstructor
public class MonthlyService {
    private final MonthlyRepo monthlyRepo;
    private final ScheduleService scheduleService;

    public void add(MonthlyPlan newPlan) {
        monthlyRepo.save(newPlan);
    }

    public Daily getToday() {
        //Choose actual Month from Database else create actual Month
        LocalDate dateToday = LocalDate.now();
        Optional<MonthlyPlan> actualMonth = monthlyRepo.findByMonth(dateToday.getMonth());
        MonthlyPlan month;
        if (actualMonth.isEmpty()) {
            MonthlyPlan monthlyPlan = new MonthlyPlan(IdService.uuid(), Month.from(dateToday.getMonth()), new ArrayList<>());
            add(monthlyPlan);
            month = monthlyPlan;
        } else {
            month = actualMonth.get();
        }

        //Search for today else create today
        List<Daily> dailys = new ArrayList<>(month.getDays());
        Daily today = new Daily();
        today.setDay(dateToday);
        boolean match = false;
        for (Daily findDay : dailys) {
            if (findDay.getDay().equals(dateToday)) {
                List<DailyPlan> dailyPlans = new ArrayList<>();
                for (DailyPlan timeplan : findDay.getDailyPlanList()) {
                    dailyPlans.add(setTime(timeplan));
                }
                today.setDailyPlanList(dailyPlans);
                match = true;
            }
        }
        if (!match) {
            today.setDailyPlanList(getDailyPlan());
            dailys.add(today);
            month.setDays(dailys);
            monthlyRepo.save(month);
        }
        return today;
    }

    public List<DailyPlan> getDailyPlan() {
        List<DailyPlan> returnList = new ArrayList<>();
        LocalDate dateToday = LocalDate.now();
        int weekOfYear = dateToday.get(WeekFields.of(Locale.getDefault()).weekOfYear());
        WorkSchedule weekList = scheduleService.getWorkSchedule(weekOfYear);
        for (ShiftSchedule shift : weekList.getDrivers()) {
            if (shift.getDay().equals(dateToday)) {
                for (WorkShift workShift : shift.getShifts()) {
                    DailyPlan plan = new DailyPlan();
                    plan.setEmployeeId(workShift.getEmployeeId());
                    plan.setTime(0);
                    returnList.add(plan);
                }
            }
        }
        for (ShiftSchedule shift : weekList.getKitchen()) {
            if (shift.getDay().equals(dateToday)) {
                for (WorkShift workShift : shift.getShifts()) {
                    DailyPlan plan = new DailyPlan();
                    plan.setEmployeeId(workShift.getEmployeeId());
                    plan.setTime(0);
                    returnList.add(plan);
                }
            }
        }
        return returnList;
    }

    public Daily saveDaily(Daily daily) {
        Daily updateDaily = new Daily();
        LocalDate dateToday = LocalDate.now();
        MonthlyPlan actualMonth = monthlyRepo.findByMonth(dateToday.getMonth())
                .orElseThrow(() -> new NoSuchElementException("Month plan not found!"));
        List<Daily> dailys = new ArrayList<>();
        for (Daily findDay : actualMonth.getDays()) {
            if (findDay.getDay().equals(daily.getDay())) {
                List<DailyPlan> updateList = new ArrayList<>();
                for (DailyPlan updateTime : daily.getDailyPlanList()) {
                    updateList.add(setTime(updateTime));
                }
                updateDaily.setDay(daily.getDay());
                updateDaily.setDailyPlanList(updateList);
                dailys.add(updateDaily);
            } else
                dailys.add(findDay);
        }
        actualMonth.setDays(dailys);
        monthlyRepo.save(actualMonth);
        return updateDaily;
    }

    private DailyPlan setTime(DailyPlan dailyplan) {
        long newTime = 0;
        long breakTime = 0;
        if (dailyplan.getStart() != null) {
            newTime = MINUTES.between(dailyplan.getStart(), LocalTime.now());
            if (dailyplan.getPause() != null) {
                for (Pause pause : dailyplan.getPause()) {
                    if (pause.getStart() != null && pause.getEnd() == null)
                        newTime = MINUTES.between(dailyplan.getStart(), pause.getStart());
                    if (pause.getStart() != null && pause.getEnd() != null)
                        breakTime += MINUTES.between(pause.getStart(), pause.getEnd());
                }
            }
            if (dailyplan.getEnd() != null) {
                newTime = MINUTES.between(dailyplan.getStart(), dailyplan.getEnd());
            }
            newTime = newTime - breakTime;
        }
        dailyplan.setTime(newTime);
        return dailyplan;
    }
}

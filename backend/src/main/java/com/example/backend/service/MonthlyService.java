package com.example.backend.service;

import com.example.backend.entities.MonthlyPlan;
import com.example.backend.entities.WorkSchedule;
import com.example.backend.model.monthly.Daily;
import com.example.backend.model.monthly.DailyPlan;
import com.example.backend.model.schedule.ShiftSchedule;
import com.example.backend.model.shift.WorkShift;
import com.example.backend.repository.MonthlyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MonthlyService {
    private final MonthlyRepo monthlyRepo;
    private final ScheduleService scheduleService;

    public void add(MonthlyPlan newPlan){
        monthlyRepo.save(newPlan);
    }
    public Daily getToday(){
        //Choose actual Month from Database else create actual Month
        LocalDate dateToday = LocalDate.now();
        Optional<MonthlyPlan> actualMonth = monthlyRepo.findByMonth(dateToday.getMonth());
        MonthlyPlan month = new MonthlyPlan();
        if(actualMonth.isEmpty()) {
            add(new MonthlyPlan(IdService.uuid(), Month.from(dateToday.getMonth()), new ArrayList<>()));
            getToday();
        }else{
            month = actualMonth.get();
        }

        //Search for today else create today
        List<Daily> dailys = new ArrayList<>();
        dailys.addAll(month.getDays());
        Daily today = new Daily();
        today.setDay(dateToday);
        boolean match = false;
        for (Daily findDay : dailys) {
            if(findDay.getDay().equals(dateToday)){
                today.setDailyPlanList(findDay.getDailyPlanList());
                match = true;
            }
        }
        if(!match){
            today.setDailyPlanList(getDailyPlan());
            dailys.add(today);
            month.setDays(dailys);
            monthlyRepo.save(month);
        }
        return today;
    }

    public List<DailyPlan> getDailyPlan(){
        List<DailyPlan> returnList = new ArrayList<>();
        LocalDate dateToday = LocalDate.now();
        int weekOfYear = dateToday.get(WeekFields.of(Locale.getDefault()).weekOfYear());
        WorkSchedule weekList = scheduleService.getWorkSchedule(weekOfYear);
        for (ShiftSchedule shift : weekList.getDrivers()) {
            if(shift.getDay().equals(dateToday)){
                for (WorkShift workShift : shift.getShifts()) {
                    DailyPlan plan = new DailyPlan();
                    plan.setEmployeeId(workShift.getEmployeeId());
                    returnList.add(plan);
                }
            }
        }
        for (ShiftSchedule shift : weekList.getKitchen()) {
            if(shift.getDay().equals(dateToday)){
                for (WorkShift workShift : shift.getShifts()) {
                    DailyPlan plan = new DailyPlan();
                    plan.setEmployeeId(workShift.getEmployeeId());
                    returnList.add(plan);
                }
            }
        }
        return returnList;
    }
}

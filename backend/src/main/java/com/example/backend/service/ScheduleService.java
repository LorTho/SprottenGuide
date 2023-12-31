package com.example.backend.service;

import com.example.backend.entities.WorkSchedule;
import com.example.backend.model.schedule.ShiftSchedule;
import com.example.backend.model.schedule.WishSchedule;
import com.example.backend.model.shift.Shifts;
import com.example.backend.model.shift.ShiftsWithDayString;
import com.example.backend.model.shift.WorkShift;
import com.example.backend.repository.ScheduleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepo scheduleRepo;

    public WorkSchedule getWorkSchedule(int nameToFind) {
        Optional<WorkSchedule> getWorkSchedule = scheduleRepo.findByName(nameToFind);
        return getWorkSchedule.orElseGet(() -> createWorkSchedule(nameToFind));
    }

    public WorkSchedule createWorkSchedule(int weekNumber) {
        WorkSchedule getWorkSchedule = scheduleRepo.findByName(0)
                .orElseThrow(() -> new RuntimeException("No Default Schedule found!"));
        WorkSchedule workSchedule = new WorkSchedule(IdService.uuid(), 999, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        LocalDate monday = getFirstDayOfWeek(2023, weekNumber);

        List<ShiftSchedule> createDriver = new ArrayList<>();
        List<ShiftSchedule> createKitchen = new ArrayList<>();

        long counter = 0;
        for (ShiftSchedule driver : getWorkSchedule.getDrivers()) {
            driver.setDay(monday.plusDays(counter));
            counter++;
            createDriver.add(driver);
        }
        counter = 0;
        for (ShiftSchedule kitchen : getWorkSchedule.getKitchen()) {
            kitchen.setDay(monday.plusDays(counter));
            counter++;
            createKitchen.add(kitchen);
        }
        workSchedule.setName(weekNumber);
        workSchedule.setDrivers(createDriver);
        workSchedule.setKitchen(createKitchen);
        workSchedule.setWishes(new ArrayList<>());
        scheduleRepo.save(workSchedule);
        return workSchedule;
    }

    public static LocalDate getFirstDayOfWeek(int year, int weekNumber) {
        return LocalDate.ofYearDay(year, 1)
                .plus((weekNumber), ChronoUnit.WEEKS)
                .with(WeekFields.ISO.dayOfWeek(), 1);
    }

    public WorkSchedule addWorkSchedule(WorkSchedule newSchedule) {
        List<WorkSchedule> allWorkSchedules = scheduleRepo.findAll();
        for (WorkSchedule findWorkSchedule : allWorkSchedules) {
            if (findWorkSchedule.getName() == newSchedule.getName()) {
                newSchedule.setId(findWorkSchedule.getId());
            }
        }
        scheduleRepo.save(newSchedule);
        return newSchedule;
    }

    public List<Shifts> getEmployeeShifts(String employeeId, int name) {
        List<Shifts> getList = new ArrayList<>();
        WorkSchedule schedule = getWorkSchedule(name);
        for (ShiftSchedule shiftSchedule : schedule.getDrivers()) {
            for (WorkShift workShift : shiftSchedule.getShifts()) {
                if (workShift.getEmployeeId().equals(employeeId)) {
                    getList.add(new Shifts(shiftSchedule.getDay(), workShift.getStartTime()));
                }
            }
        }
        for (ShiftSchedule shiftSchedule : schedule.getKitchen()) {
            for (WorkShift workShift : shiftSchedule.getShifts()) {
                if (workShift.getEmployeeId().equals(employeeId)) {
                    getList.add(new Shifts(shiftSchedule.getDay(), workShift.getStartTime()));
                }
            }
        }
        return getList;
    }

    public List<ShiftsWithDayString> getEmployeeWishes(String employeeId, int name) {
        WorkSchedule schedule = getWorkSchedule(name);
        for (WishSchedule wish : schedule.getWishes()) {
            if (wish.getEmployeeId().equals(employeeId)) {
                List<ShiftsWithDayString> listToReturn = new ArrayList<>();
                for (Shifts shift : wish.getShifts()) {
                    listToReturn.add(new ShiftsWithDayString(shift.getDay().getDayOfWeek().toString(), shift.getStartTime()));
                }
                return listToReturn;
            }
        }
        return Collections.emptyList();
    }

    public List<Shifts> saveEmployeeWishes(String employeeId, int name, List<ShiftsWithDayString> newWishList) {
        WorkSchedule schedule = getWorkSchedule(name);
        List<Shifts> newList = new ArrayList<>();

        LocalDate monday = getFirstDayOfWeek(2023, name);
        for (ShiftsWithDayString shift : newWishList) {
            DayOfWeek setDay = DayOfWeek.valueOf(shift.getDay());
            if (!setDay.equals(DayOfWeek.MONDAY))
                newList.add(new Shifts(monday.with(TemporalAdjusters.next(setDay)), shift.getStartTime()));
            else
                newList.add(new Shifts(monday, shift.getStartTime()));
        }

        boolean match = false;
        for (WishSchedule wish : schedule.getWishes()) {
            if (wish.getEmployeeId().equals(employeeId)) {
                match = true;
                wish.setShifts(newList);
            }
        }
        if (!match) {
            WishSchedule newWishSchedule = new WishSchedule(employeeId, newList);
            schedule.getWishes().add(newWishSchedule);
        }
        scheduleRepo.save(schedule);
        return newList;
    }
}

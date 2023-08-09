package com.example.backend.service;

import com.example.backend.entities.WorkSchedule;
import com.example.backend.model.schedule.ShiftSchedule;
import com.example.backend.model.schedule.WishSchedule;
import com.example.backend.model.shift.Shifts;
import com.example.backend.model.shift.WorkShift;
import com.example.backend.repository.ScheduleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
        return LocalDate.ofYearDay(year, 1) // Erster Tag des Jahres
                .plus((weekNumber), ChronoUnit.WEEKS) // Füge die Kalenderwochen hinzu
                .with(WeekFields.ISO.dayOfWeek(), 1); // Setze auf Montag
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
        WorkSchedule schedule = scheduleRepo.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("WorkSchedule for Employees not found!"));
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

    public List<Shifts> getEmployeeWishes(String employeeId, int name) {
        WorkSchedule schedule = scheduleRepo.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("WorkSchedule for Wishes not found!"));
        for (WishSchedule wish : schedule.getWishes()) {
            if (wish.getEmployeeId().equals(employeeId)) {
                return wish.getShifts();
            }
        }
        return Collections.emptyList();
    }

    public List<Shifts> saveEmployeeWishes(String employeeId, int name, List<Shifts> newList) {
        WorkSchedule schedule = scheduleRepo.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("WorkSchedule not found!"));
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

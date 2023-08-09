package com.example.backend.service;

import com.example.backend.entities.WorkSchedule;
import com.example.backend.model.schedule.ShiftSchedule;
import com.example.backend.model.schedule.WishSchedule;
import com.example.backend.model.shift.Shifts;
import com.example.backend.model.shift.WorkShift;
import com.example.backend.repository.ScheduleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepo scheduleRepo;

    public WorkSchedule getWorkSchedule(String nameToFind) {
       Optional<WorkSchedule> getWorkschedule = scheduleRepo.findByName(nameToFind);
        return getWorkschedule.orElseGet(() -> scheduleRepo.findByName("defaultSchedule")
                .orElseThrow(() -> new NoSuchElementException("No defaultSchedule existing! please contact your admin")));
    }

    public WorkSchedule addWorkSchedule(WorkSchedule newSchedule) {
        List<WorkSchedule> allWorkSchedules = scheduleRepo.findAll();
        for (WorkSchedule findWorkSchedule : allWorkSchedules) {
            if (findWorkSchedule.getName().equals(newSchedule.getName())) {
                newSchedule.setId(findWorkSchedule.getId());
            }
        }
        scheduleRepo.save(newSchedule);
        return newSchedule;
    }

    public List<Shifts> getEmployeeShifts(String employeeId, String name) {
        List<Shifts> getList = new ArrayList<>();
        WorkSchedule schedule = scheduleRepo.findByName(name)
                .orElseThrow(()->new NoSuchElementException("WorkSchedule for Employees not found!"));
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
    public List<Shifts> getEmployeeWishes(String employeeId, String name) {
        WorkSchedule schedule = scheduleRepo.findByName(name)
                .orElseThrow(()->new NoSuchElementException("WorkSchedule for Wishes not found!"));
        for (WishSchedule wish : schedule.getWishes()) {
            if (wish.getEmployeeId().equals(employeeId)) {
                return wish.getShifts();
            }
        }
        return Collections.emptyList();
    }

    public List<Shifts> saveEmployeeWishes(String employeeId, String name, List<Shifts> newList) {
        WorkSchedule schedule = scheduleRepo.findByName(name)
                .orElseThrow(()->new NoSuchElementException("WorkSchedule not found!"));
        boolean match = false;
        for (WishSchedule wish : schedule.getWishes()) {
            if (wish.getEmployeeId().equals(employeeId)) {
                match = true;
                wish.setShifts(newList);
            }
        }
        if(!match){
            WishSchedule newWishSchedule = new WishSchedule(employeeId, newList);
            schedule.getWishes().add(newWishSchedule);
        }
        scheduleRepo.save(schedule);
        return newList;
    }
}

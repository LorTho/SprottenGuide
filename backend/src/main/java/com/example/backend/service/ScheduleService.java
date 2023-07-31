package com.example.backend.service;

import com.example.backend.model.*;
import com.example.backend.repository.ScheduleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepo scheduleRepo;
    private final EmployeeService employeeService;

    public WorkScheduleExport getWorkSchedule(String name) {
        WorkScheduleExport getSchedule = new WorkScheduleExport();
        getSchedule.setName(name);
        List<WorkSchedule> list = scheduleRepo.findAll();
        for (WorkSchedule listB : list) {
            if (Objects.equals(listB.getName(), name)) {
                List<ShiftSchedule> getDriversShift = new ArrayList<>();
                List<ShiftSchedule> getKitchenShift = new ArrayList<>();

                for (ShiftSchedule listC : listB.getDrivers()) {
                    getDriversShift.add(getNamesByIdFromEmployeeDB(listC));
                }
                for (ShiftSchedule listC : listB.getKitchen()) {
                    getKitchenShift.add(getNamesByIdFromEmployeeDB(listC));
                }
                getSchedule.setDrivers(getDriversShift);
                getSchedule.setKitchen(getKitchenShift);
            }
        }
        return getSchedule;
    }

    public ShiftSchedule getNamesByIdFromEmployeeDB(ShiftSchedule listC){
        Employee employee;
        ShiftSchedule getShift = new ShiftSchedule(listC.getDay(), new ArrayList<>());
        List<WorkShift> getWorkShifts = new ArrayList<>();
        for (WorkShift listD : listC.getShifts()) {
            employee = employeeService.getEmployee(listD.getEmployeeId());
            WorkShift replaceShift = new WorkShift(employee.getFirstName(), listD.getStartTime());
            getWorkShifts.add(replaceShift);
        }
        getShift.setShifts(getWorkShifts);
        return getShift;
    }
    public WorkSchedule addWorkSchedule(WorkSchedule newSchedule) {
        scheduleRepo.insert(newSchedule);
        return newSchedule;
    }
}

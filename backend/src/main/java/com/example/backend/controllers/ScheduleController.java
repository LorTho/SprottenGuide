package com.example.backend.controllers;

import com.example.backend.entities.WorkSchedule;
import com.example.backend.model.schedule.WorkScheduleNoId;
import com.example.backend.model.shift.Shifts;
import com.example.backend.service.IdService;
import com.example.backend.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;
    public ScheduleController(ScheduleService scheduleService){
        this.scheduleService=scheduleService;
    }

    @GetMapping("/{weekNumber}")
    public WorkSchedule getWorkSchedule (@PathVariable int weekNumber) {
        return scheduleService.getWorkSchedule(weekNumber);
    }
    @GetMapping("/{employeeId}/{weekNumber}")
    public List<Shifts> getEmployeeShifts(@PathVariable String employeeId, @PathVariable int weekNumber){
        return scheduleService.getEmployeeShifts(employeeId, weekNumber);
    }
    @GetMapping("/{employeeId}/{weekNumber}/wish")
    public List<Shifts> getEmployeeWish(@PathVariable String employeeId, @PathVariable int weekNumber){
        return scheduleService.getEmployeeWishes(employeeId, weekNumber);
    }
    @PutMapping("/{employeeId}/{weekNumber}")
    public List<Shifts> saveEmployeeWishes(@PathVariable String employeeId, @PathVariable int weekNumber, @RequestBody List<Shifts> newList){
        return scheduleService.saveEmployeeWishes(employeeId, weekNumber, newList);
    }

    @PutMapping
    public WorkSchedule saveWorkSchedule(@RequestBody WorkScheduleNoId newWorkScheduleNoId){
        WorkSchedule newWorkSchedule = new WorkSchedule(IdService.uuid(), newWorkScheduleNoId.getName(),newWorkScheduleNoId.getDrivers(),newWorkScheduleNoId.getKitchen(), newWorkScheduleNoId.getWishes());
        return scheduleService.addWorkSchedule(newWorkSchedule);
    }

}

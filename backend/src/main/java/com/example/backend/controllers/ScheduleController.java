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

    @GetMapping("/{name}")
    public WorkSchedule getWorkSchedule (@PathVariable String name) {
        return scheduleService.getWorkSchedule(name);
    }
    @GetMapping("/{employeeId}/{name}")
    public List<Shifts> getEmployeeShifts(@PathVariable String employeeId, @PathVariable String name){
        return scheduleService.getEmployeeShifts(employeeId, name);
    }
    @GetMapping("/{employeeId}/{name}/wish")
    public List<Shifts> getEmployeeWish(@PathVariable String employeeId, @PathVariable String name){
        return scheduleService.getEmployeeWishes(employeeId, name);
    }
    @PutMapping("/{employeeId}/{name}")
    public List<Shifts> saveEmployeeWishes(@PathVariable String employeeId, @PathVariable String name, @RequestBody List<Shifts> newList){
        return scheduleService.saveEmployeeWishes(employeeId, name, newList);
    }

    @PutMapping
    public WorkSchedule saveWorkSchedule(@RequestBody WorkScheduleNoId newWorkScheduleNoId){
        WorkSchedule newWorkSchedule = new WorkSchedule(IdService.uuid(), newWorkScheduleNoId.getName(),newWorkScheduleNoId.getDrivers(),newWorkScheduleNoId.getKitchen(), newWorkScheduleNoId.getWishes());
        return scheduleService.addWorkSchedule(newWorkSchedule);
    }

}

package com.example.backend.controllers;

import com.example.backend.model.schedule.WorkSchedule;
import com.example.backend.model.schedule.WorkScheduleNoId;
import com.example.backend.service.IdService;
import com.example.backend.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;
    public ScheduleController(ScheduleService scheduleService){
        this.scheduleService=scheduleService;
    }

    @GetMapping("/{name}")
    public WorkScheduleNoId getWorkSchedule (@PathVariable String name) {
        return scheduleService.getWorkSchedule(name);
    }

    @PutMapping
    public WorkSchedule saveWorkSchedule(@RequestBody WorkScheduleNoId newWorkScheduleNoId){
        WorkSchedule newWorkSchedule = new WorkSchedule(IdService.uuid(), newWorkScheduleNoId.getName(),newWorkScheduleNoId.getDrivers(),newWorkScheduleNoId.getKitchen());
        return scheduleService.addWorkSchedule(newWorkSchedule);
    }

}

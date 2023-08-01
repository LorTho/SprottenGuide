package com.example.backend.controllers;

import com.example.backend.model.WorkScheduleExport;
import com.example.backend.service.ScheduleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;
    public ScheduleController(ScheduleService scheduleService){
        this.scheduleService=scheduleService;
    }

    @GetMapping("/{name}")
    public WorkScheduleExport getWorkSchedule (@PathVariable String name) {
        return scheduleService.getWorkSchedule(name);
    }
}

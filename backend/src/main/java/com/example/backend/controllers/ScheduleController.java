package com.example.backend.controllers;

import com.example.backend.model.WorkSchedule;
import com.example.backend.service.ScheduleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;
    public ScheduleController(ScheduleService scheduleService){
        this.scheduleService=scheduleService;
    }

    @GetMapping
    public List<WorkSchedule> getWorkSchedule () {
        return scheduleService.getWorkSchedule();
    }
}

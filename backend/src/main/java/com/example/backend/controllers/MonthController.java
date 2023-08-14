package com.example.backend.controllers;

import com.example.backend.model.monthly.Daily;
import com.example.backend.service.MonthlyService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/month")
public class MonthController {
    private final MonthlyService monthlyService;
    public MonthController(MonthlyService monthlyService) {
        this.monthlyService = monthlyService;
    }

    @GetMapping("/today")
    public Daily getToday(){
        return monthlyService.getToday();
    }

    @PutMapping("/save")
    public Daily saveDaily(@RequestBody Daily daily){
        return monthlyService.saveDaily(daily);
    }
}

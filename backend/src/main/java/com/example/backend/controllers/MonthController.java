package com.example.backend.controllers;

import com.example.backend.model.monthly.Daily;
import com.example.backend.service.MonthlyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

package com.example.backend.controllers;

import com.example.backend.model.Time;
import com.example.backend.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}/{week}")
    public List<Time> getWeeklyTime(@PathVariable String id, @PathVariable String week) {
        return employeeService.getWeeklyTime(id, week);
    }
}

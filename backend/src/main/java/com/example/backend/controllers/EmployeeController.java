package com.example.backend.controllers;

import com.example.backend.model.EmployeeWithoutTimes;
import com.example.backend.model.Employee;
import com.example.backend.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public Employee addEmployee(@RequestBody EmployeeWithoutTimes newDtoEmployee) {
        Employee newEmployee = new Employee("NoId", newDtoEmployee.getFirstName(), newDtoEmployee.getLastName(), null, null);
        return employeeService.addEmployee(newEmployee);
    }

}

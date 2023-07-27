package com.example.backend.controllers;

import com.example.backend.model.EmployeeWithoutShifts;
import com.example.backend.model.Employee;
import com.example.backend.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping({"/{id}"})
    public Employee getEmployee(@PathVariable String id){
        return employeeService.getEmployee(id);
    }
    @PostMapping
    public Employee addEmployee(@RequestBody EmployeeWithoutShifts newEmployee) {
        Employee employee = new Employee(newEmployee.getId(), newEmployee.getFirstName(), newEmployee.getLastName(), new ArrayList<>(), new ArrayList<>());
        return employeeService.addEmployee(employee);
    }
}

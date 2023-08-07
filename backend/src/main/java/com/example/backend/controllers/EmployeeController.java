package com.example.backend.controllers;

import com.example.backend.model.employee.EmployeeWithoutShifts;
import com.example.backend.entities.Employee;

import com.example.backend.model.shift.RequestShift;
import com.example.backend.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<EmployeeWithoutShifts> getEmployeeList(){
        return employeeService.getEmployeeList();
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
    @PutMapping("/{id}")
    public Employee changeWishTime(@PathVariable String id, @RequestBody List<RequestShift> wishList) {
        return employeeService.changeWishTime(id, wishList);
    }
}

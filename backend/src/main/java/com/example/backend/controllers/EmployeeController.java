package com.example.backend.controllers;

import com.example.backend.entities.Employee;
import com.example.backend.model.employee.EmployeeDTO;
import com.example.backend.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getEmployeeList(){
        return employeeService.getEmployeeList();
    }
    @GetMapping({"/{id}"})
    public Employee getEmployee(@PathVariable String id){
        return employeeService.getEmployee(id);
    }
    @PostMapping
    public Employee addEmployee(@RequestBody EmployeeDTO newEmployee) {
        Employee employee = new Employee(newEmployee.getMemberCode(), newEmployee.getFirstName(), newEmployee.getLastName());
        return employeeService.addEmployee(employee);
    }
}

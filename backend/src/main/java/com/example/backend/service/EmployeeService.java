package com.example.backend.service;

import com.example.backend.entities.Employee;
import com.example.backend.repository.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepo employeeRepo;

    public Employee addEmployee(Employee newEmployee) {
        employeeRepo.insert(newEmployee);
        return newEmployee;
    }

    public Employee getEmployee(String id) {
        Optional<Employee> employee = employeeRepo.findById(id);
        return employee.orElseGet(() -> new Employee("0", "--", "--"));
    }
    public List<Employee> getEmployeeList() {
        return employeeRepo.findAll();
    }
}

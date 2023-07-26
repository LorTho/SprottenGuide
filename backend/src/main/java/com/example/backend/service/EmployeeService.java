package com.example.backend.service;

import com.example.backend.model.Employee;
import com.example.backend.repository.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepo employeeRepo;

    public Employee addEmployee(Employee newEmployee) {
        newEmployee.setThisWeek(new ArrayList<>());
        newEmployee.setNextWeek(new ArrayList<>());

        employeeRepo.insert(newEmployee);
        return newEmployee;
    }

    public Employee getEmployee(String id) {
        Optional<Employee> employee = employeeRepo.findById(id);
        if (employee.isPresent()) {
            return employee.get();
        } else {
            throw new NoSuchElementException("employee not found!");
        }
    }
}

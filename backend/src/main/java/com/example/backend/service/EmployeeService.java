package com.example.backend.service;

import com.example.backend.model.Employee;
import com.example.backend.model.IdService;
import com.example.backend.model.Time;
import com.example.backend.model.WeekInitializer;
import com.example.backend.repository.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepo employeeRepo;

    public Employee addEmployee(Employee newEmployee) {
        newEmployee.setId(IdService.uuid());

        Map<Integer, List<Time>> actualWeek;
        Map<Integer, List<Time>> nextWeek;
        actualWeek = WeekInitializer.createWeek(0);
        nextWeek = WeekInitializer.createWeek(7);

        newEmployee.setThisWeek(actualWeek);
        newEmployee.setNextWeek(nextWeek);

        employeeRepo.insert(newEmployee);
        return newEmployee;
    }
}

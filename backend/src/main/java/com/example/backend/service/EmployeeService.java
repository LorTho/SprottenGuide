package com.example.backend.service;

import com.example.backend.model.Employee;
import com.example.backend.model.Time;
import com.example.backend.repository.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepo employeeRepo;

    public List<Time> getWeeklyTime(String id, String week) {

        Optional<Employee> employee = employeeRepo.findById(id);
        if (employee.isPresent()) {
            return employee.get().getWeeklyTime().get(week);
        } else {
            throw new NoSuchElementException("employee not found!");
        }
    }
}

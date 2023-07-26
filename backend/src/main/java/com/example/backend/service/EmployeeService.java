package com.example.backend.service;

import com.example.backend.model.Employee;
import com.example.backend.model.Time;
import com.example.backend.repository.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepo employeeRepo;

    public Employee addEmployee(Employee newEmployee){
        newEmployee.setId(UUID.randomUUID().toString());

        Map<Integer, List<Time>> actualWeek;
        Map<Integer, List<Time>> nextWeek;
        actualWeek= (createWeek(0));
        nextWeek = createWeek(7);

        newEmployee.setThisWeek(actualWeek);
        newEmployee.setNextWeek(nextWeek);

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

    public static Map<Integer, List<Time>> createWeek(int day){
        LocalDate today = LocalDate.now().plusDays(day);
        LocalDate firstDayOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        ArrayList<Time> days = new ArrayList<>();
        Time date;
        for (int i = 0;i<7;i++){
            date = new Time(firstDayOfWeek.plusDays(i), LocalTime.MIN);
            days.add(date);
        }

        Map<Integer, List<Time>> week = new HashMap<>();
        week.put(today.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()), days);
        return week;
    }
}

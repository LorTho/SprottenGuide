package com.example.backend.service;

import com.example.backend.model.Employee;
import com.example.backend.model.RequestShift;
import com.example.backend.model.Shifts;
import com.example.backend.repository.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
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

    public Employee changeWishTime(String id, List<RequestShift> wishList){
        Optional<Employee> employee = employeeRepo.findById(id);
        if (employee.isPresent()) {
            List<Shifts> shiftList= new ArrayList<>();
            wishList.forEach(e ->{
                Shifts time = new Shifts(e.getDay(), LocalTime.parse(e.getStartTime()));
                shiftList.add(time);
            });
            employee.get().setNextWeek(shiftList);
            employeeRepo.save(employee.get());
            return employee.get();
        } else {
            throw new NoSuchElementException("employee not found!");
        }
    }
}

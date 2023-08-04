package com.example.backend.service;

import com.example.backend.model.employee.Employee;
import com.example.backend.model.employee.EmployeeWithoutShifts;
import com.example.backend.model.shift.RequestShift;
import com.example.backend.model.shift.Shifts;
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
        return employee.orElseGet(() -> new Employee("0", "--", "--", new ArrayList<>(), new ArrayList<>()));
    }
    public List<EmployeeWithoutShifts> getEmployeeList() {
        List<Employee> allEmployee = employeeRepo.findAll();
        List<EmployeeWithoutShifts> listToReturn = new ArrayList<>();
        for (Employee employee : allEmployee) {
            EmployeeWithoutShifts addEmployeeToList = new EmployeeWithoutShifts();
            addEmployeeToList.setId(employee.getId());
            addEmployeeToList.setFirstName(employee.getFirstName());
            addEmployeeToList.setLastName(employee.getLastName());
            listToReturn.add(addEmployeeToList);
        }
        return listToReturn;
    }
    public Employee changeWishTime(String id, List<RequestShift> wishList){
        Optional<Employee> employee = employeeRepo.findById(id);
        if (employee.isPresent()) {
            List<Shifts> shiftList= new ArrayList<>();
            wishList.forEach(e ->{
                Shifts time = new Shifts(e.day(), LocalTime.parse(e.startTime()));
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

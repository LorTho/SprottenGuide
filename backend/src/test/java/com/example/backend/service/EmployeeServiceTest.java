package com.example.backend.service;

import com.example.backend.model.DtoEmployee;
import com.example.backend.model.Employee;
import com.example.backend.model.Time;
import com.example.backend.repository.EmployeeRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {
    EmployeeRepo employeeRepo = mock(EmployeeRepo.class);
    EmployeeService employeeService = new EmployeeService(employeeRepo);

    @Test
    void getEmployeeWithInitialList_whenAddNewEmployee(){
        //Given
        Employee newEmployee = new Employee("NoId", "test", "test", null, null);

        //When
        Employee actualEmployee = employeeService.addEmployee(newEmployee);
        //Then
        Assertions.assertEquals(newEmployee, actualEmployee);
    }
}
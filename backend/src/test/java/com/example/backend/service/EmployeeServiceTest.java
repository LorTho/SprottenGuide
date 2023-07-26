package com.example.backend.service;

import com.example.backend.model.Employee;
import com.example.backend.repository.EmployeeRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
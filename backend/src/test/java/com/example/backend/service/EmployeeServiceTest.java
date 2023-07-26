package com.example.backend.service;

import com.example.backend.model.Employee;
import com.example.backend.repository.EmployeeRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.*;

class EmployeeServiceTest {
    EmployeeRepo employeeRepo = mock(EmployeeRepo.class);
    EmployeeService employeeService = new EmployeeService(employeeRepo);

    @Test
    void getEmployee_whenAddNewEmployee(){
        //Given
        Employee newEmployee = new Employee("NoId", "test", "test", null, null);

        //When
        Employee actualEmployee = employeeService.addEmployee(newEmployee);
        //Then
        Assertions.assertEquals(newEmployee, actualEmployee);
    }

    @Test
    void getEmployee_WhenGetById(){
        Employee newEmployee = new Employee("1111", "test", "test", null, null);

        when(employeeRepo.findById("1111")).thenReturn(Optional.of(newEmployee));
        Employee actualEmployee = employeeService.getEmployee("1111");

        verify(employeeRepo).findById("1111");
        Assertions.assertEquals(newEmployee, actualEmployee);
    }
    @Test
    void getNoSuchElementException_WhenGetByWrongId(){
        Assertions.assertThrows(NoSuchElementException.class, () -> employeeService.getEmployee("wrongId"));
    }
}
package com.example.backend.service;

import com.example.backend.entities.Employee;
import com.example.backend.model.shift.RequestShift;
import com.example.backend.repository.EmployeeRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.*;

class EmployeeServiceTest {
    EmployeeRepo employeeRepo = mock(EmployeeRepo.class);
    EmployeeService employeeService = new EmployeeService(employeeRepo);

    @Test
    void getEmployee_whenAddNewEmployee(){
        //Given
        Employee newEmployee = new Employee("NoId", "test", "test");

        //When
        Employee actualEmployee = employeeService.addEmployee(newEmployee);
        //Then
        Assertions.assertEquals(newEmployee, actualEmployee);
    }

    @Test
    void getEmployee_WhenGetById(){
        Employee newEmployee = new Employee("1111", "test", "test");

        when(employeeRepo.findById("1111")).thenReturn(Optional.of(newEmployee));
        Employee actualEmployee = employeeService.getEmployee("1111");

        verify(employeeRepo).findById("1111");
        Assertions.assertEquals(newEmployee, actualEmployee);
    }
    @Test
    void getDummyEmployee_WhenGetByWrongId(){
        Employee employee = new Employee("0", "--", "--");

        Assertions.assertEquals(employee, employeeService.getEmployee("wrongId"));
    }


    @Test
    void getNoSuchElementException_WhenSaveWishListWithWrongId(){
        List<RequestShift> wishList = new ArrayList<>(List.of(
                new RequestShift("MONDAY", "11:00:00")
        ));
        Assertions.assertThrows(NoSuchElementException.class, () -> employeeService.changeWishTime("wrongId", wishList));
    }
    @Test
    void getEmployeeListWithoutShifts_WhenGetAllEmployees(){
        Employee newEmployee = new Employee("1111", "test", "test");

        List<Employee> expectedList = new ArrayList<>(List.of(newEmployee));

        when(employeeRepo.findAll()).thenReturn(List.of(newEmployee));
        List<Employee> actualEmployeeList = employeeService.getEmployeeList();

        verify(employeeRepo).findAll();
        Assertions.assertEquals(expectedList, actualEmployeeList);
    }
}

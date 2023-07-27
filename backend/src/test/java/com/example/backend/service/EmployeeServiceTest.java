package com.example.backend.service;

import com.example.backend.model.Employee;
import com.example.backend.model.RequestShift;
import com.example.backend.model.Shifts;
import com.example.backend.repository.EmployeeRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
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

    @Test
    void getEmployeeWithUpdatedList_WhenSaveWishList(){
        Employee newEmployee = new Employee("1111", "test", "test", null, List.of(
                new Shifts("MONDAY", LocalTime.parse("11:00:00"))
        ));

        when(employeeRepo.findById("1111")).thenReturn(Optional.of(newEmployee));
        List<RequestShift> wishList = new ArrayList<>(List.of(
                new RequestShift("MONDAY", "11:00:00")
        ));
        Employee actualEmployee = employeeService.changeWishTime("1111", wishList);

        verify(employeeRepo).findById("1111");
        Assertions.assertEquals(newEmployee, actualEmployee);
    }
    @Test
    void getNoSuchElementException_WhenSaveWishListWithWrongId(){
        List<RequestShift> wishList = new ArrayList<>(List.of(
                new RequestShift("MONDAY", "11:00:00")
        ));
        Assertions.assertThrows(NoSuchElementException.class, () -> employeeService.changeWishTime("wrongId", wishList));
    }
}
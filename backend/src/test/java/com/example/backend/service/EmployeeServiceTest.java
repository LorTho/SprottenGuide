package com.example.backend.service;

import com.example.backend.model.Employee;
import com.example.backend.model.Time;
import com.example.backend.repository.EmployeeRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {
    EmployeeRepo employeeRepo = mock(EmployeeRepo.class);
    EmployeeService employeeService = new EmployeeService(employeeRepo);

    @Test
    void getListOfWeeklyTimes_whengetWeeklyTimeisCalled(){
        //Given
        HashMap<Integer, List<Time>> weekly = new HashMap<>();
        weekly.put(29,
                List.of(new Time("14.07", "10.00 Uhr"),
                        new Time("15.07", "11.00 Uhr"),
                        new Time("16.07", "12.00 Uhr"),
                        new Time("17.07", "13.00 Uhr"),
                        new Time("18.07", "14.00 Uhr"),
                        new Time("19.07", "15.00 Uhr")));
        Employee emp1 = new Employee("123", "Lorenz", "Thoms", weekly);

        //When
        when(employeeRepo.findById("123")).thenReturn(Optional.of(emp1));
        Employee actual = employeeService.getEmployee("123");

        //Then
        assertEquals(emp1, actual);
    }
    @Test
    void NoSuchElement_whengetWeeklyTimeisCalled(){
        //When
        when(employeeRepo.findById("123")).thenReturn(null);
        //Then
        Assertions.assertThrows(NoSuchElementException.class, () -> employeeService.getEmployee("0000"));
    }


}
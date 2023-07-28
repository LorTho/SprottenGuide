package com.example.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class ShiftSchedule {
    private String day;
    private String employeeID;
    private LocalTime startTime;
}

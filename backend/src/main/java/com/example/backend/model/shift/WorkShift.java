package com.example.backend.model.shift;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class WorkShift {
    private String employeeId;
    private LocalTime startTime;
}

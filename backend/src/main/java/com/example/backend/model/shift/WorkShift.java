package com.example.backend.model.shift;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkShift {
    private String employeeId;
    private int startTime;
}

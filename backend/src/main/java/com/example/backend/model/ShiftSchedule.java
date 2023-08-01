package com.example.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ShiftSchedule {
    private String day;
    private List<WorkShift> shifts;
}

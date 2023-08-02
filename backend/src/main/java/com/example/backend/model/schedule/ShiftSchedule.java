package com.example.backend.model.schedule;

import com.example.backend.model.shift.WorkShift;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ShiftSchedule {
    private String day;
    private List<WorkShift> shifts;
}

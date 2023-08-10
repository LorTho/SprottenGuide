package com.example.backend.model.schedule;

import com.example.backend.model.shift.WorkShift;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class ShiftSchedule {
    private LocalDate day;
    private List<WorkShift> shifts;
}

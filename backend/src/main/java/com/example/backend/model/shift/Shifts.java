package com.example.backend.model.shift;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class Shifts {
    private LocalDate day;
    private LocalTime startTime;
}

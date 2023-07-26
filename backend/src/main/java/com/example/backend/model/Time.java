package com.example.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class Time {
    private LocalDate date;
    private LocalTime startTime;
}

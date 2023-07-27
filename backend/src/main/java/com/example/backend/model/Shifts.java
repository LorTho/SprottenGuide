package com.example.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class Shifts {
    private String day;
    private LocalTime startTime;
}

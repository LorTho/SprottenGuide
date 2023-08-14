package com.example.backend.model.monthly;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class DailyPlan {
    String employeeId;
    LocalTime start;
    LocalTime end;
    List<Pause> pause;
    Double time;
}

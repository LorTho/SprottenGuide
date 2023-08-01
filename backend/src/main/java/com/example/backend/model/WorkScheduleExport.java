package com.example.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class WorkScheduleExport {
    private String name;
    private List<ShiftSchedule> drivers;
    private List<ShiftSchedule> kitchen;
}

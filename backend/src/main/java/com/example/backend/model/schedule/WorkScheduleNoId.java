package com.example.backend.model.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class WorkScheduleNoId {
    private int name;
    private List<ShiftSchedule> drivers;
    private List<ShiftSchedule> kitchen;
    private List<WishSchedule> wishes;
}

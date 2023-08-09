package com.example.backend.model.monthly;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Data
public class Daily {
    LocalDate day;
    List<DailyPlan> dailyPlanList;
}

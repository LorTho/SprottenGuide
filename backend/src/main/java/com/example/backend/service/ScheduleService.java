package com.example.backend.service;

import com.example.backend.model.WorkSchedule;
import com.example.backend.repository.ScheduleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepo scheduleRepo;

    public List<WorkSchedule> getWorkSchedule(){
        return scheduleRepo.findAll();
    }
    public WorkSchedule addWorkSchedule(WorkSchedule newSchedule){
        scheduleRepo.insert(newSchedule);
        return newSchedule;
    }
}

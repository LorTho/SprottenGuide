package com.example.backend.service;
import com.example.backend.entities.WorkSchedule;
import com.example.backend.model.schedule.WorkScheduleNoId;
import com.example.backend.repository.ScheduleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepo scheduleRepo;

    public WorkScheduleNoId getWorkSchedule(String nameToFind) {
        WorkScheduleNoId getSchedule = new WorkScheduleNoId();
        boolean findSuccess = false;

        List<WorkSchedule> list = scheduleRepo.findAll();
        for (WorkSchedule listB : list) {
            if (Objects.equals(listB.getName(), nameToFind)) {
                findSuccess = true;
                getSchedule.setName(nameToFind);
                getSchedule.setDrivers(listB.getDrivers());
                getSchedule.setKitchen(listB.getKitchen());
            }
        }
        if(!findSuccess) {
            WorkSchedule defaultSchedule = scheduleRepo.findById("1234567890")
                    .orElseThrow(()-> new NoSuchElementException("No defaultSchedule existing! please contact your admin"));
                getSchedule.setName(defaultSchedule.getName());
                getSchedule.setDrivers(defaultSchedule.getDrivers());
                getSchedule.setKitchen(defaultSchedule.getKitchen());
        }
        return getSchedule;
    }

    public WorkSchedule addWorkSchedule(WorkSchedule newSchedule) {
        List<WorkSchedule> allWorkSchedules = scheduleRepo.findAll();
        for (WorkSchedule findWorkSchedule : allWorkSchedules) {
            if (findWorkSchedule.getName().equals(newSchedule.getName())) {
                newSchedule.setId(findWorkSchedule.getId());
            }
        }
        scheduleRepo.save(newSchedule);
        return newSchedule;
    }
}

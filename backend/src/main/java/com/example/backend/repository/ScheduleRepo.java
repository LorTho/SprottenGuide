package com.example.backend.repository;

import com.example.backend.model.WorkSchedule;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScheduleRepo extends MongoRepository<WorkSchedule, String> {
}

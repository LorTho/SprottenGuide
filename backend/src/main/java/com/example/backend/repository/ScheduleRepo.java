package com.example.backend.repository;

import com.example.backend.entities.WorkSchedule;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ScheduleRepo extends MongoRepository<WorkSchedule, String> {
    Optional<WorkSchedule> findByName(int name);
}

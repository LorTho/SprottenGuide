package com.example.backend.repository;

import com.example.backend.entities.WorkSchedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScheduleRepo extends MongoRepository<WorkSchedule, String> {
    Optional<WorkSchedule> findByName(int name);
}

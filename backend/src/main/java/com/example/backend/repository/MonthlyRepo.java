package com.example.backend.repository;

import com.example.backend.entities.MonthlyPlan;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Month;
import java.util.Optional;

public interface MonthlyRepo extends MongoRepository<MonthlyPlan, String> {
    Optional<MonthlyPlan> findByMonth(Month month);
}
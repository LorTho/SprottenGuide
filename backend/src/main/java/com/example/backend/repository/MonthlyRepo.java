package com.example.backend.repository;

import com.example.backend.entities.MonthlyPlan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.util.Optional;

@Repository
public interface MonthlyRepo extends MongoRepository<MonthlyPlan, String> {
    Optional<MonthlyPlan> findByMonth(Month month);
}

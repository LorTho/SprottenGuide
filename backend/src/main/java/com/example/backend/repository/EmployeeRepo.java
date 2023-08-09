package com.example.backend.repository;

import com.example.backend.entities.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepo extends MongoRepository<Employee, String> {
}

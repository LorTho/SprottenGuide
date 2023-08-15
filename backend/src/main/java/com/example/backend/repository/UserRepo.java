package com.example.backend.repository;

import com.example.backend.entities.MongoUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<MongoUser, String> {
}

package com.example.backend.repository;

import com.example.backend.entities.MongoUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<MongoUser, String> {
}

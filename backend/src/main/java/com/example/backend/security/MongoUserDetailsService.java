package com.example.backend.security;

import com.example.backend.entities.MongoUser;
import com.example.backend.repository.UserRepo;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MongoUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;

    public MongoUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        MongoUser mongoUser = userRepo.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User " + userId + " not found!"));
        return new User(mongoUser.getId(), mongoUser.getPassword(), Collections.emptyList());
    }
}

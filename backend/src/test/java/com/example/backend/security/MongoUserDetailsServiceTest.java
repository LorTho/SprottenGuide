package com.example.backend.security;

import com.example.backend.entities.MongoUser;
import com.example.backend.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.mockito.Mockito.*;

class MongoUserDetailsServiceTest {
    UserRepo userRepo = mock(UserRepo.class);
    MongoUserDetailsService mongoUserDetailsService = new MongoUserDetailsService(userRepo);
    @Test
    void loadUserByUsername() {
        String givenUserName = "0000";
        MongoUser expectedUser = new MongoUser("0000", "Hans", "Hausmeister","hans1");
        when(userRepo.findById(givenUserName)).thenReturn(Optional.of(expectedUser));
        UserDetails actualUser = mongoUserDetailsService.loadUserByUsername(givenUserName);
        verify(userRepo).findById(givenUserName);
        Assertions.assertEquals(givenUserName, actualUser.getUsername());
    }

    @Test
    void expectUsernameNotFoundException_whenFindByUsername() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> mongoUserDetailsService.loadUserByUsername("WrongId"));
    }
}
package com.example.backend.service;

import com.example.backend.entities.MongoUser;
import com.example.backend.model.user.UserDTO;
import com.example.backend.repository.UserRepo;
import com.example.backend.security.Role;
import com.example.backend.security.UserSecurity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class MongoUserServiceTest {
    UserRepo userRepo = mock(UserRepo.class);
    PasswordEncoder passwordEncoder =Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    UserService userService = new UserService(userRepo, passwordEncoder);

    @Test
    void getTrue_whenAddNewEmployee(){
        //Given
        UserSecurity newMongoUser = new UserSecurity("NoId", "test", "test", "1234", Role.USER);
        MongoUser expectedUser = new MongoUser("NoId", "test", "test", "1234", Role.USER);

        //When
        Boolean boolReturn = userService.addUser(newMongoUser);
        //Then
        Assertions.assertEquals(true, boolReturn);
    }

    @Test
    void getEmployee_WhenGetById(){
        MongoUser newMongoUser = new MongoUser("1111", "test", "test", "1234", Role.USER);
        UserDTO expectedUser = new UserDTO("1111", "test", "test", Role.USER);

        when(userRepo.findById("1111")).thenReturn(Optional.of(newMongoUser));
        UserDTO actualUser = userService.getUser("1111");

        verify(userRepo).findById("1111");
        Assertions.assertEquals(expectedUser, actualUser);
    }
    @Test
    void getDummyEmployee_WhenGetByWrongId(){
        UserDTO user = new UserDTO("0", "--", "--", Role.USER);

        Assertions.assertEquals(user, userService.getUser("wrongId"));
    }

    @Test
    void getEmployeeList_WhenGetAllEmployees(){
        MongoUser newMongoUser = new MongoUser("1111", "test", "test","", Role.USER);

        List<UserDTO> expectedList = new ArrayList<>(List.of(new UserDTO(newMongoUser.getId(), newMongoUser.getFirstName(), newMongoUser.getLastName(), newMongoUser.getRole())));

        when(userRepo.findAll()).thenReturn(List.of(newMongoUser));
        List<UserDTO> actualUserList = userService.getUserList();

        verify(userRepo).findAll();
        Assertions.assertEquals(expectedList, actualUserList);
    }
}

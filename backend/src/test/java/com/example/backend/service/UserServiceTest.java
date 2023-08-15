package com.example.backend.service;

import com.example.backend.entities.User;
import com.example.backend.model.user.UserDTO;
import com.example.backend.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class UserServiceTest {
    UserRepo userRepo = mock(UserRepo.class);
    UserService userService = new UserService(userRepo);

    @Test
    void getEmployee_whenAddNewEmployee(){
        //Given
        User newUser = new User("NoId", "test", "test", "1234");

        //When
        User actualUser = userService.addUser(newUser);
        //Then
        Assertions.assertEquals(newUser, actualUser);
    }

    @Test
    void getEmployee_WhenGetById(){
        User newUser = new User("1111", "test", "test", "1234");
        UserDTO expectedUser = new UserDTO("1111", "test", "test");

        when(userRepo.findById("1111")).thenReturn(Optional.of(newUser));
        UserDTO actualUser = userService.getUser("1111");

        verify(userRepo).findById("1111");
        Assertions.assertEquals(expectedUser, actualUser);
    }
    @Test
    void getDummyEmployee_WhenGetByWrongId(){
        UserDTO user = new UserDTO("0", "--", "--");

        Assertions.assertEquals(user, userService.getUser("wrongId"));
    }

    @Test
    void getEmployeeList_WhenGetAllEmployees(){
        User newUser = new User("1111", "test", "test","");

        List<UserDTO> expectedList = new ArrayList<>(List.of(new UserDTO(newUser.getId(), newUser.getFirstName(), newUser.getLastName())));

        when(userRepo.findAll()).thenReturn(List.of(newUser));
        List<UserDTO> actualUserList = userService.getUserList();

        verify(userRepo).findAll();
        Assertions.assertEquals(expectedList, actualUserList);
    }
}

package com.example.backend.controllers;

import com.example.backend.entities.User;
import com.example.backend.model.user.UserDTO;
import com.example.backend.security.LoginData;
import com.example.backend.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getUserList(){
        return userService.getUserList();
    }
    @GetMapping({"/{id}"})
    public UserDTO getUser(@PathVariable String id){
        return userService.getUser(id);
    }
    @PostMapping
    public User addUser(@RequestBody UserDTO newUser) {
        User user = new User(newUser.getMemberCode(), newUser.getFirstName(), newUser.getLastName(), "");
        return userService.addUser(user);
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginData loginData) {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}

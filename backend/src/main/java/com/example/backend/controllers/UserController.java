package com.example.backend.controllers;

import com.example.backend.entities.MongoUser;
import com.example.backend.model.user.UserDTO;
import com.example.backend.security.UserSecurity;
import com.example.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public List<UserDTO> getUserList() {
        return userService.getUserList();
    }

    @GetMapping({"/{id}"})
    public UserDTO getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    @PostMapping("/add")
    public MongoUser addUser(@RequestBody UserSecurity newUser){
        return userService.addUser(newUser);
    }
    @GetMapping
    public String getUserInfo(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    @PostMapping("/login")
    public String login() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    @PostMapping("/logout")
    public void logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response){
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, authentication);
    }
}

package com.example.backend.controllers;

import com.example.backend.model.user.UserDTO;
import com.example.backend.model.user.UserObject;
import com.example.backend.security.jwt.JwtService;
import com.example.backend.security.LoginData;
import com.example.backend.security.UserSecurity;
import com.example.backend.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
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
    public Boolean addUser(@RequestBody UserSecurity newUser){
        return userService.addUser(newUser);
    }
    @GetMapping
    public UserObject getUserInfo(){
        String memberCode = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDTO user = userService.getUser(memberCode);
        return new UserObject(memberCode, user.getRole());
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginData loginData) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginData.userCode(), loginData.password()));
        return jwtService.createToken(loginData.userCode());
    }
}

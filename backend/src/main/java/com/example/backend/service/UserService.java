package com.example.backend.service;

import com.example.backend.entities.User;
import com.example.backend.model.user.UserDTO;
import com.example.backend.repository.UserRepo;
import com.example.backend.security.UserSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();


    public User addUser(User newUser) {
        userRepo.insert(newUser);
        return newUser;
    }

    public UserDTO getUser(String id) {
        User user = userRepo.findById(id)
                .orElseGet(()-> new User("0", "--", "--", ""));
        return new UserDTO(user.getId(), user.getFirstName(), user.getLastName());
    }
    public List<UserDTO> getUserList() {
        List<User> userList = userRepo.findAll();
        List<UserDTO> returnList = new ArrayList<>();
        for (User user : userList) {
            returnList.add(new UserDTO(user.getId(), user.getFirstName(), user.getLastName()));
        }
        return returnList;
    }

    public String signUp(UserSecurity userSecurity) {
        String hashedPassword = encoder.encode(userSecurity.password());
        User newUser = new User(userSecurity.memberCode(), userSecurity.firstName(), userSecurity.lastName(), hashedPassword);
        userRepo.insert(newUser);
        return newUser.getFirstName();
    }
}

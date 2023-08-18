package com.example.backend.service;

import com.example.backend.entities.MongoUser;
import com.example.backend.model.user.UserDTO;
import com.example.backend.repository.UserRepo;
import com.example.backend.security.Role;
import com.example.backend.security.UserSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserDTO getUser(String id) {
        MongoUser mongoUser = userRepo.findById(id)
                .orElseGet(()-> new MongoUser("0", "--", "--", "", Role.USER));
        return new UserDTO(mongoUser.getId(), mongoUser.getFirstName(), mongoUser.getLastName(), mongoUser.getRole());
    }
    public List<UserDTO> getUserList() {
        List<MongoUser> mongoUserList = userRepo.findAll();
        List<UserDTO> returnList = new ArrayList<>();
        for (MongoUser mongoUser : mongoUserList) {
            returnList.add(new UserDTO(mongoUser.getId(), mongoUser.getFirstName(), mongoUser.getLastName(), mongoUser.getRole()));
        }
        return returnList;
    }
    public Boolean addUser(UserSecurity newUser){
        MongoUser newMongoUser = new MongoUser(
                newUser.memberCode(),
                newUser.firstName(),
                newUser.lastName(),
                passwordEncoder.encode(newUser.password()),
                newUser.role());
        userRepo.insert(newMongoUser);
        return true;
    }
}

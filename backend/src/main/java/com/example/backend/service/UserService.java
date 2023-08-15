package com.example.backend.service;

import com.example.backend.entities.MongoUser;
import com.example.backend.model.user.UserDTO;
import com.example.backend.repository.UserRepo;
import com.example.backend.security.UserSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public UserDTO getUser(String id) {
        MongoUser mongoUser = userRepo.findById(id)
                .orElseGet(()-> new MongoUser("0", "--", "--", ""));
        return new UserDTO(mongoUser.getId(), mongoUser.getFirstName(), mongoUser.getLastName());
    }
    public List<UserDTO> getUserList() {
        List<MongoUser> mongoUserList = userRepo.findAll();
        List<UserDTO> returnList = new ArrayList<>();
        for (MongoUser mongoUser : mongoUserList) {
            returnList.add(new UserDTO(mongoUser.getId(), mongoUser.getFirstName(), mongoUser.getLastName()));
        }
        return returnList;
    }
    public MongoUser addUser(UserSecurity newUser){
        MongoUser newMongoUser = new MongoUser(newUser.memberCode(), newUser.firstName(), newUser.lastName(), newUser.password());
        userRepo.insert(newMongoUser);
        return newMongoUser;
    }
}

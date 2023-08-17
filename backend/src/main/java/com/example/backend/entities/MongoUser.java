package com.example.backend.entities;

import com.example.backend.security.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document("user")
public class MongoUser{
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String password;
    private Role role;
}

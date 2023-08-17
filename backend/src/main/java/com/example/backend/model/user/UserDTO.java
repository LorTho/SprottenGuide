package com.example.backend.model.user;

import com.example.backend.security.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private String memberCode;
    private String firstName;
    private String lastName;
    private Role role;
}

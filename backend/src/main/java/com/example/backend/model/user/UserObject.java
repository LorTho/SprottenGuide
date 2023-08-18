package com.example.backend.model.user;

import com.example.backend.security.Role;

public record UserObject(
        String memberCode,
        Role role
) {
}

package com.example.backend.security;

public record UserSecurity(
        String memberCode,
        String firstName,
        String lastName,
        String password,
        Role role
) {
}

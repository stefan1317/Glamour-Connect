package com.example.glamour_connect.dto;

import lombok.Builder;

@Builder
public record AppUserDto(
        long id,
        String name,
        String email,
        String password,
        String phone,
        String address,
        String role) {
}

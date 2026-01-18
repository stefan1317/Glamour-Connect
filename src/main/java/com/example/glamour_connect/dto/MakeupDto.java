package com.example.glamour_connect.dto;

import lombok.Builder;

@Builder
public record MakeupDto(
        long id,
        String name,
        String description,
        String imageUrl,
        double durationInHours,
        double price) {
}

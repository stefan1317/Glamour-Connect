package com.example.glamour_connect.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AppointmentDto(
        long id,
        LocalDateTime dateTime,
        double durationInHours,
        long userId,
        long makeupId,
        String status) {
}

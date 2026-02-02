package org.heymouad.bookingmanagementsystem.dtos;

import java.util.UUID;

public record FitnessClassesResponseDto(
        UUID id,
        String name,
        String description,
        int durationMinutes,
        int capacity,
        String category
) {
}

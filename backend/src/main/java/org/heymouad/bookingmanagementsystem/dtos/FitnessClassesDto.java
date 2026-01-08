package org.heymouad.bookingmanagementsystem.dtos;

public record FitnessClassesDto(
        String name,
        String description,
        int durationMinutes,
        int capacity,
        String category) {
}

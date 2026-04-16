package org.heymouad.bookingmanagementsystem.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FitnessClassesDto(
        @NotBlank
        @Size(max = 100)
        String name,

        @Size(max = 500)
        String description,

        @Min(1) int durationMinutes,
        @Min(1) int capacity,

        @NotBlank
        String category
) {}

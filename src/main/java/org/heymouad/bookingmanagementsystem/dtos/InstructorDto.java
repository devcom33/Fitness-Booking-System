package org.heymouad.bookingmanagementsystem.dtos;

import java.util.UUID;

public record InstructorDto(
        UUID userID,
        String bio,
        String specialization) {
}

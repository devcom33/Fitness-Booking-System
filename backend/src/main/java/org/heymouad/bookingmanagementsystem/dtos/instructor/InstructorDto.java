package org.heymouad.bookingmanagementsystem.dtos.instructor;

import java.util.UUID;

public record InstructorDto(
        UUID userID,
        String bio,
        String specialization) {
}

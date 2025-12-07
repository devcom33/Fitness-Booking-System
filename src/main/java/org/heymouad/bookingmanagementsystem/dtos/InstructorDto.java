package org.heymouad.bookingmanagementsystem.dtos;

import java.util.UUID;

public record InstructorDto(UUID id, UserResponseDto userResponseDtoDto, String bio, String specialization) {
}

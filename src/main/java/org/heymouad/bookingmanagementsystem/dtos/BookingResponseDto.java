package org.heymouad.bookingmanagementsystem.dtos;

import org.heymouad.bookingmanagementsystem.enums.BookingStatus;

import java.time.ZonedDateTime;
import java.util.UUID;

public record BookingResponseDto(UUID id,
                                 UserRegistrationRequestDto userRegistrationRequestDto,
                                 ClassSchedulesDto classSchedulesDto,
                                 InstructorDto instructorDto,
                                 ZonedDateTime startTime,
                                 ZonedDateTime endTime,
                                 BookingStatus status) {
}

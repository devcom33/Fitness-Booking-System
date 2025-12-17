package org.heymouad.bookingmanagementsystem.dtos;

import lombok.Builder;
import org.heymouad.bookingmanagementsystem.enums.BookingStatus;

import java.time.ZonedDateTime;
import java.util.UUID;

@Builder
public record BookingResponseDto(
        UUID id,
        UUID userId,
        String userName,
        UUID classScheduleId,
        String fitnessClassName,
        String instructorName,
        ZonedDateTime startTime,
        ZonedDateTime endTime,
        BookingStatus status
) {}

package org.heymouad.bookingmanagementsystem.dtos;

import java.util.UUID;

public record BookingRequestDto(
        UUID userId,
        UUID classScheduleId) {
}

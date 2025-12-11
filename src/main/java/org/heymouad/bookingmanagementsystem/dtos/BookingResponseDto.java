package org.heymouad.bookingmanagementsystem.dtos;

import org.heymouad.bookingmanagementsystem.enums.BookingStatus;

import java.time.ZonedDateTime;
import java.util.UUID;

public record BookingResponseDto(UUID id,
                                 UserDto userDto,
                                 ClassScheduleSummaryDto classSchedulesDto,
                                 BookingStatus status) {
}

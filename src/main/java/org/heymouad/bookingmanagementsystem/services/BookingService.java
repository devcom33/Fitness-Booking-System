package org.heymouad.bookingmanagementsystem.services;

import org.heymouad.bookingmanagementsystem.dtos.BookingRequestDto;
import org.heymouad.bookingmanagementsystem.dtos.BookingResponseDto;

public interface BookingService {
    BookingResponseDto createBooking(BookingRequestDto bookingRequestDto);
}

package org.heymouad.bookingmanagementsystem.mappers;


import org.heymouad.bookingmanagementsystem.dtos.BookingRequestDto;
import org.heymouad.bookingmanagementsystem.dtos.BookingResponseDto;
import org.heymouad.bookingmanagementsystem.entities.Booking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    Booking toBooking(BookingRequestDto bookingRequestDto);
    BookingResponseDto toBookingResponseDto(Booking booking);
}

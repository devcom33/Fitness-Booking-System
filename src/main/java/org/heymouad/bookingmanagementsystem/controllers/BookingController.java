package org.heymouad.bookingmanagementsystem.controllers;

import lombok.RequiredArgsConstructor;
import org.heymouad.bookingmanagementsystem.dtos.BookingRequestDto;
import org.heymouad.bookingmanagementsystem.dtos.BookingResponseDto;
import org.heymouad.bookingmanagementsystem.entities.Booking;
import org.heymouad.bookingmanagementsystem.enums.BookingStatus;
import org.heymouad.bookingmanagementsystem.mappers.BookingMapper;
import org.heymouad.bookingmanagementsystem.services.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RequestMapping("/api/v1/bookings/")
@RestController
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    @PostMapping
    public ResponseEntity<BookingResponseDto> createBooking(@RequestBody BookingRequestDto bookingRequestDto)
    {
        Booking booking = bookingMapper.toBooking(bookingRequestDto);
        Booking createdBooking = bookingService.createBooking(booking);

        return ResponseEntity.ok(bookingMapper.toBookingResponseDto(createdBooking));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<BookingResponseDto> updateBookingStatus(@PathVariable UUID id, @RequestBody BookingStatus newStatus) {
        Booking booking = bookingService.updateBookingStatus(id, newStatus);

        return ResponseEntity.ok(bookingMapper.toBookingResponseDto(booking));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDto> getBookingById(@PathVariable UUID id)
    {
        Booking booking = bookingService.getBookingById(id);

        return ResponseEntity.ok(bookingMapper.toBookingResponseDto(booking));
    }

    @GetMapping
    public ResponseEntity<List<BookingResponseDto>> getAllBookings()
    {
        List<BookingResponseDto> bookingResponseDtoList = bookingService.getAllBookings().stream().map(bookingMapper::toBookingResponseDto).toList();
        return ResponseEntity.ok(bookingResponseDtoList);
    }
}

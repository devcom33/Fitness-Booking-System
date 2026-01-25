package org.heymouad.bookingmanagementsystem.controllers;

import lombok.RequiredArgsConstructor;
import org.heymouad.bookingmanagementsystem.dtos.BookingRequestDto;
import org.heymouad.bookingmanagementsystem.dtos.BookingResponseDto;
import org.heymouad.bookingmanagementsystem.dtos.StatusUpdateRequest;
import org.heymouad.bookingmanagementsystem.entities.Booking;
import org.heymouad.bookingmanagementsystem.enums.BookingStatus;
import org.heymouad.bookingmanagementsystem.mappers.BookingMapper;
import org.heymouad.bookingmanagementsystem.services.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;


@RequestMapping(path="/api/v1/bookings/", produces = "application/json")
@RestController
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    @PostMapping
    public ResponseEntity<BookingResponseDto> createBooking(@RequestBody BookingRequestDto bookingRequestDto, Principal principal)
    {
        String userEmail = principal.getName();

        Booking createdBooking = bookingService.createBooking(userEmail, bookingRequestDto.classScheduleId());

        return ResponseEntity.ok(bookingMapper.toResponseDto(createdBooking));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<BookingResponseDto> updateBookingStatus(@PathVariable UUID id, @RequestBody StatusUpdateRequest statusUpdateRequest) {

        BookingStatus bookingStatus = BookingStatus.valueOf(statusUpdateRequest.status());
        Booking booking = bookingService.updateBookingStatus(id, bookingStatus);

        return ResponseEntity.ok(bookingMapper.toResponseDto(booking));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDto> getBookingById(@PathVariable UUID id)
    {
        Booking booking = bookingService.getBookingById(id);

        return ResponseEntity.ok(bookingMapper.toResponseDto(booking));
    }

    @GetMapping
    public ResponseEntity<List<BookingResponseDto>> getAllBookings()
    {
        List<BookingResponseDto> bookingResponseDtoList = bookingService.getAllBookings().stream().map(bookingMapper::toResponseDto).toList();
        return ResponseEntity.ok(bookingResponseDtoList);
    }

    @GetMapping("/my-bookings")
    public ResponseEntity<List<BookingResponseDto>> getMyBookings(Principal principal)
    {
        String userEmail = principal.getName();
        List<BookingResponseDto> bookingResponseDtoList = bookingService.getMyBookings(userEmail).stream().map(bookingMapper::toResponseDto).toList();
        return ResponseEntity.ok(bookingResponseDtoList);
    }
}

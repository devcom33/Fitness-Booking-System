package org.heymouad.bookingmanagementsystem.services;

import org.heymouad.bookingmanagementsystem.entities.Booking;
import org.heymouad.bookingmanagementsystem.enums.BookingStatus;

import java.util.List;
import java.util.UUID;

public interface BookingService {
    Booking createBooking(Booking booking);
    Booking updateBookingStatus(UUID id, BookingStatus newStatus);
    Booking getBookingById(UUID id);
    List<Booking> getAllBookings();
}

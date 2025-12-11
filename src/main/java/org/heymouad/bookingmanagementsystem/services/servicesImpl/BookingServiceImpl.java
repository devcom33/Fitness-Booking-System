package org.heymouad.bookingmanagementsystem.services.servicesImpl;

import lombok.RequiredArgsConstructor;
import org.heymouad.bookingmanagementsystem.dtos.BookingRequestDto;
import org.heymouad.bookingmanagementsystem.dtos.ClassSchedulesDto;
import org.heymouad.bookingmanagementsystem.entities.Booking;
import org.heymouad.bookingmanagementsystem.entities.ClassSchedules;
import org.heymouad.bookingmanagementsystem.entities.User;
import org.heymouad.bookingmanagementsystem.enums.BookingStatus;
import org.heymouad.bookingmanagementsystem.exceptions.CapacityExceededException;
import org.heymouad.bookingmanagementsystem.exceptions.ResourceNotFoundException;
import org.heymouad.bookingmanagementsystem.repositories.BookingRepository;
import org.heymouad.bookingmanagementsystem.services.BookingService;
import org.heymouad.bookingmanagementsystem.services.ClassSchedulesService;
import org.heymouad.bookingmanagementsystem.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Transactional
@RequiredArgsConstructor
@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final ClassSchedulesService classSchedulesService;

    /**
     * Creates a new booking, checks capacity, and links persistent entities
     * @param booking The initial Booking entity containing User and Schedule IDs
     * @return The newly created and saved Booking entity
     * @throws CapacityExceededException If the class is full
     */
    @Override
    public Booking createBooking(Booking booking) {
        UUID userId = booking.getUser().getId();
        UUID scheduleId = booking.getClassSchedules().getId();

        User user = userService.getUserById(userId);
        ClassSchedules classSchedules = classSchedulesService.getClassScheduleById(scheduleId);

        // Capacity Check
        int maxCapacity = classSchedules.getFitnessClasses().getCapacity();
        List<BookingStatus> holdingStatuses = List.of(BookingStatus.CONFIRMED, BookingStatus.PENDING);
        long bookedCount = bookingRepository.countByClassSchedulesIdAndStatusIn(scheduleId, holdingStatuses);

        if (bookedCount >= maxCapacity) {
            throw new CapacityExceededException("No remaining booking capacity for class ID: " + scheduleId);
        }

        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setUser(user);
        booking.setClassSchedules(classSchedules);

        return bookingRepository.save(booking);
    }

    /**
     * Updates the status of an existing booking.
     * @param id The ID of the booking to update
     * @param newStatus The new status like CANCELLED etc
     * @return The updated Booking entity
     * @throws ResourceNotFoundException If the booking ID is not found
     * @throws IllegalStateException If the booking is already in a terminal state
     */
    @Override
    public Booking updateBookingStatus(UUID id, BookingStatus newStatus) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking ", id));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new IllegalStateException("Cannot update status of a cancelled booking.");
        }

        booking.setStatus(newStatus);

        return bookingRepository.save(booking);
    }


    /**
     * Retrieves a single booking by its ID
     * @param id The ID of the booking
     * @return The requested Booking entity
     * @throws ResourceNotFoundException If the booking is not found
     */
    @Override
    public Booking getBookingById(UUID id) {
        return bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking ", id));
    }


    /**
     * Retrieves all existing bookings from the database
     * @return A list of all Booking entities
     */
    @Override
    public List<Booking> getAllBookings()
    {
        return bookingRepository.findAll();
    }
}
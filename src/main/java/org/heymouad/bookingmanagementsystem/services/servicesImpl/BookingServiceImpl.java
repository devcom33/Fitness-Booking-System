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
import org.heymouad.bookingmanagementsystem.mappers.BookingMapper;
import org.heymouad.bookingmanagementsystem.mappers.ClassSchedulesMapper;
import org.heymouad.bookingmanagementsystem.mappers.UserMapper;
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
    private final BookingMapper bookingMapper;

    @Override
    public Booking createBooking(Booking booking) {
        UUID userId = booking.getUser().getId();
        UUID scheduleId = booking.getClassSchedules().getId();

        User user = userService.getUserById(userId);
        ClassSchedules classSchedules = classSchedulesService.getClassScheduleById(scheduleId);

        int maxCapacity = classSchedules.getFitnessClasses().getCapacity();

        // Count confirmed and pending
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

    @Override
    public Booking updateBooking(UUID id, Booking updateBooking) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking ", id));
        ClassSchedules classSchedules = classSchedulesService.getClassScheduleById(updateBooking.getClassSchedules().getId());

        booking.setClassSchedules(classSchedules);

        bookingRepository.save(booking);

        return booking;
    }

    @Override
    public Booking getBookingById(UUID id) {

        return bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking ", id));
    }


    @Override
    public List<Booking> getAllBookings()
    {
        return bookingRepository.findAll();
    }
}

package org.heymouad.bookingmanagementsystem.services.servicesImpl;

import lombok.RequiredArgsConstructor;
import org.heymouad.bookingmanagementsystem.dtos.BookingRequestDto;
import org.heymouad.bookingmanagementsystem.dtos.BookingResponseDto;
import org.heymouad.bookingmanagementsystem.entities.Booking;
import org.heymouad.bookingmanagementsystem.enums.BookingStatus;
import org.heymouad.bookingmanagementsystem.exceptions.CapacityExceededException;
import org.heymouad.bookingmanagementsystem.mappers.BookingMapper;
import org.heymouad.bookingmanagementsystem.repositories.BookingRepository;
import org.heymouad.bookingmanagementsystem.services.BookingService;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    @Override
    public BookingResponseDto createBooking(BookingRequestDto bookingRequestDto) {
        long confirmed =  bookingRepository.findByStatus(BookingStatus.CONFIRMED);

        if (bookingRequestDto.classSchedulesDto().fitnessClassesDto().capacity() == confirmed) {
            throw new CapacityExceededException("No remaining booking capacity");
        }

        Booking booking = bookingMapper.toBooking(bookingRequestDto);

        return bookingMapper.toBookingResponseDto(bookingRepository.save(booking));
    }
}

package org.heymouad.bookingmanagementsystem.services.servicesImpl;

import lombok.RequiredArgsConstructor;
import org.heymouad.bookingmanagementsystem.dtos.admin.BookingMonthlyCountDTO;
import org.heymouad.bookingmanagementsystem.dtos.admin.DashboardStatsDTO;
import org.heymouad.bookingmanagementsystem.enums.BookingStatus;
import org.heymouad.bookingmanagementsystem.enums.ScheduleStatus;
import org.heymouad.bookingmanagementsystem.enums.UserRole;
import org.heymouad.bookingmanagementsystem.repositories.BookingRepository;
import org.heymouad.bookingmanagementsystem.repositories.ClassScheduleRepository;
import org.heymouad.bookingmanagementsystem.repositories.UserRepository;
import org.heymouad.bookingmanagementsystem.services.AdminDashboardService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@RequiredArgsConstructor
@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final ClassScheduleRepository classSchedulesRepository;


    @Override
    public DashboardStatsDTO getDashboardStats() {
        return DashboardStatsDTO.builder()
                .totalInstructors(userRepository.countAllByRoleName(UserRole.INSTRUCTOR))
                .totalClients(userRepository.countAllByRoleName(UserRole.CLIENT))
                .totalConfirmedBookings(bookingRepository.countAllByStatus(BookingStatus.CONFIRMED))
                .totalScheduledSessions(classSchedulesRepository.countAllByStatus(ScheduleStatus.SCHEDULED))
                .build();
    }

    @Override
    public List<BookingMonthlyCountDTO> getLastMonthsStats(int months) {
        LocalDateTime startDate = LocalDateTime.now().minusMonths(months);
        return bookingRepository.countBookingsPerMonth(startDate);
    }
}

package org.heymouad.bookingmanagementsystem.repositories;

import org.heymouad.bookingmanagementsystem.dtos.admin.BookingMonthlyCountDTO;
import org.heymouad.bookingmanagementsystem.entities.Booking;
import org.heymouad.bookingmanagementsystem.entities.ClassSchedules;
import org.heymouad.bookingmanagementsystem.entities.User;
import org.heymouad.bookingmanagementsystem.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {

    long countByClassSchedulesIdAndStatusIn(UUID scheduleId, List<BookingStatus> bookingStatusList);
    boolean existsByClassSchedulesAndUserAndStatusIn(ClassSchedules classSchedules, User user, List<BookingStatus> status);

    List<Booking> findByUser(User user, Pageable pageable);

    int countByClassSchedulesIdAndStatus(UUID classSchedulesId, BookingStatus status);

    long countAllByStatus(BookingStatus status);

    @Query(
        """
        SELECT new org.heymouad.bookingmanagementsystem.dtos.admin.BookingMonthlyCountDTO(
                MONTH(b.createdAt), YEAR(b.createdAt), COUNT(b)
                )
                        FROM Booking as b
                                WHERE b.createdAt >= :startDate
                                        GROUP BY YEAR(b.createdAt), MONTH(b.createdAt)
                                                ORDER BY YEAR(b.createdAt), MONTH(b.createdAt)
        """
    )
    List<BookingMonthlyCountDTO> countBookingsPerMonth(@Param("startDate") LocalDateTime startDate);
}

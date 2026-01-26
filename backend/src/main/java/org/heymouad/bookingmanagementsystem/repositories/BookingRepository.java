package org.heymouad.bookingmanagementsystem.repositories;

import org.heymouad.bookingmanagementsystem.entities.Booking;
import org.heymouad.bookingmanagementsystem.entities.ClassSchedules;
import org.heymouad.bookingmanagementsystem.entities.User;
import org.heymouad.bookingmanagementsystem.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    long findByStatus(BookingStatus status);
    @Query("""
    SELECT b FROM Booking b
    JOIN FETCH b.user u
    JOIN FETCH b.classSchedules cs
    JOIN FETCH cs.fitnessClass fc
    JOIN FETCH cs.instructor i
""")
    List<Booking> findAllWithDetails();
    long countByClassSchedulesIdAndStatusIn(UUID scheduleId, List<BookingStatus> bookingStatusList);
    boolean existsByClassSchedulesAndUserAndStatusIn(ClassSchedules classSchedules, User user, List<BookingStatus> status);

    List<Booking> findByUser(User user, Pageable pageable);

}

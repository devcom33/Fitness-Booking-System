package org.heymouad.bookingmanagementsystem.repositories;

import org.heymouad.bookingmanagementsystem.entities.ClassSchedules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface ClassSchedulesRepository extends JpaRepository<ClassSchedules, UUID> {
    @Query("""
        SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END
            FROM ClassSchedules c
            WHERE c.instructor.id = :instructorId AND c.startTime < :endTime AND c.endTime > :startTime
    """)
    boolean existsClassSchedulesByInstructorIdAndOverlap(@Param("startTime") ZonedDateTime startTime, @Param("endTime") ZonedDateTime endTime, @Param("instructorId") UUID instructorId);

}

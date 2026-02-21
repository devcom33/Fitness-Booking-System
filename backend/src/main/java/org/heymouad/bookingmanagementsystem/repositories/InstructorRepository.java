package org.heymouad.bookingmanagementsystem.repositories;

import org.heymouad.bookingmanagementsystem.entities.Instructor;
import org.heymouad.bookingmanagementsystem.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, UUID> {
    Optional<Instructor> findByUserEmail(String email);
    @Query("""
        SELECT i
            FROM Instructor i
                WHERE i.user.userStatus = :status
    """)
    List<Instructor> findAllByUserStatus(@Param("status")UserStatus status);
}

package org.heymouad.bookingmanagementsystem.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.heymouad.bookingmanagementsystem.enums.BookingStatus;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Booking {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "class_schedule_id")
    private ClassSchedules classSchedules;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @Column(nullable = false)
    private ZonedDateTime startTime;

    @Column(nullable = false)
    private ZonedDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    @CreatedDate
    private Instant createdAt;
}

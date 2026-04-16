package org.heymouad.bookingmanagementsystem.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class FitnessClass {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    @Min(value = 1, message = "Duration must be at least 1 minute")
    private int durationMinutes;

    @Column(nullable = false)
    @Min(value = 1, message = "Capacity must be at least 1 person")
    private int capacity;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @CreatedDate
    private Instant createdAt;
}

/*
    @Column(nullable = false)
    @PositiveOrZero
    private BigDecimal price;
    The Logic
        The Rule: 1 Booking = 1 Session Price.

        - The Calculation: Total Revenue = Sum of price
        for all FitnessClass records associated with successful bookings.
*/
package org.heymouad.bookingmanagementsystem.entities;


import jakarta.persistence.*;
import lombok.*;
import org.heymouad.bookingmanagementsystem.enums.ScheduleStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.UUID;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class ClassSchedules {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "instructor_id", nullable = false)
    private Instructor instructor;

    @ManyToOne
    @JoinColumn(name = "fitness_classes_id", nullable = false)
    private FitnessClass fitnessClass;

    @Column(nullable = false)
    private ZonedDateTime startTime;

    @Column(nullable = false)
    private ZonedDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "recurring_template_id")
    private RecurringScheduleTemplate template;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScheduleStatus status = ScheduleStatus.SCHEDULED;;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant modifiedAt;
}

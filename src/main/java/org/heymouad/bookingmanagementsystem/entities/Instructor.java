package org.heymouad.bookingmanagementsystem.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "instructors")
@EntityListeners(AuditingEntityListener.class)
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // optional = false --> cannot have an instructor record without a corresponding user
    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private String bio;

    @Column(nullable = true)
    private String specialization;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant modifiedAt;
}

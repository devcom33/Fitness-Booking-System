package org.heymouad.bookingmanagementsystem.entities;

import jakarta.persistence.*;
import lombok.*;
import org.heymouad.bookingmanagementsystem.enums.UserRole;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole name;
}

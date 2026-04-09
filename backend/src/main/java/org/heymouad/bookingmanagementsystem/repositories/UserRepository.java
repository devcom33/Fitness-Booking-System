package org.heymouad.bookingmanagementsystem.repositories;

import org.heymouad.bookingmanagementsystem.entities.Role;
import org.heymouad.bookingmanagementsystem.entities.User;
import org.heymouad.bookingmanagementsystem.enums.UserRole;
import org.heymouad.bookingmanagementsystem.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    List<User> findByRoleAndUserStatus(Role role, UserStatus userStatus);

    @Query("""
        SELECT COUNT(u) FROM User u
            WHERE u.role.name = :roleName
    """)
    Long countAllByRoleName(@Param("roleName") UserRole role);
}

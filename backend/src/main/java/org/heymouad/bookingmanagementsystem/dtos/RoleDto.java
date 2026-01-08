package org.heymouad.bookingmanagementsystem.dtos;

import lombok.Builder;
import org.heymouad.bookingmanagementsystem.enums.UserRole;

@Builder
public record RoleDto(UserRole name) {
}

package org.heymouad.bookingmanagementsystem.dtos.auth;

import lombok.Builder;
import org.heymouad.bookingmanagementsystem.enums.UserRole;

@Builder
public record AuthResponseDto(String accessToken, String name, String email, UserRole role){}

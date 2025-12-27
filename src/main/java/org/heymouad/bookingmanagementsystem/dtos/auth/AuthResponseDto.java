package org.heymouad.bookingmanagementsystem.dtos.auth;

import lombok.Builder;
import org.heymouad.bookingmanagementsystem.enums.UserRole;

@Builder
public class AuthResponseDto {
    private String accessToken;
    private String name;
    private String email;
    private UserRole role;
}

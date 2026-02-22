package org.heymouad.bookingmanagementsystem.dtos.auth;

import lombok.Builder;

@Builder
public record ApplicationResponseDto(
        String message
) {}

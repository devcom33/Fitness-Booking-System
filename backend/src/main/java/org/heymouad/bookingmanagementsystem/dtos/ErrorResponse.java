package org.heymouad.bookingmanagementsystem.dtos;

import java.time.LocalDateTime;

public record ErrorResponse (
        String code,
        String message,
        LocalDateTime timestamp
){}

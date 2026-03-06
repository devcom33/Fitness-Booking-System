package org.heymouad.bookingmanagementsystem.dtos.client;

import java.util.UUID;

public record ClientResponseDto(UUID clientId, String name,
                                String email) {
}

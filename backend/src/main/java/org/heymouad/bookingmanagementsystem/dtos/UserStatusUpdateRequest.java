package org.heymouad.bookingmanagementsystem.dtos;

import jakarta.validation.constraints.NotNull;
import org.heymouad.bookingmanagementsystem.enums.UserStatus;

public record UserStatusUpdateRequest(@NotNull(message = "Status is required") UserStatus userStatus) {
}

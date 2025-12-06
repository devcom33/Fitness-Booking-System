package org.heymouad.bookingmanagementsystem.dtos;

public record UserRegistrationRequestDto(String name, String email, String password, RoleDto roleDto) {
}

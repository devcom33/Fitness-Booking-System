package org.heymouad.bookingmanagementsystem.dtos;

public record UserResponseDto(String name, String email, RoleDto roleDto) {
}
package org.heymouad.bookingmanagementsystem.dtos.instructor;

import org.heymouad.bookingmanagementsystem.dtos.RoleDto;

public record InstructorApplicationRequest(String name, String email, String password, RoleDto roleDto, String bio,
                                           String specialization) {
}

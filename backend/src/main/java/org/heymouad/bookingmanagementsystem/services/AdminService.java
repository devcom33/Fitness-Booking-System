package org.heymouad.bookingmanagementsystem.services;

import org.heymouad.bookingmanagementsystem.dtos.instructor.InstructorResponseDto;
import org.heymouad.bookingmanagementsystem.enums.UserStatus;

import java.util.List;
import java.util.UUID;

public interface AdminService {
    void updateInstructorStatus(UUID instructorId, UserStatus newStatus);
    List<InstructorResponseDto> getPendingInstructors();
    List<InstructorResponseDto> getInstructors();
}

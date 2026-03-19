package org.heymouad.bookingmanagementsystem.services;

import org.heymouad.bookingmanagementsystem.dtos.instructor.InstructorResponseDto;
import org.heymouad.bookingmanagementsystem.enums.UserStatus;

import java.util.List;
import java.util.UUID;

public interface AdminInstructorService {
    void updateInstructorStatus(UUID instructorId, UserStatus newStatus);
    void updateAccountState(UUID id, UserStatus status);
    List<InstructorResponseDto> getPendingInstructors();
    List<InstructorResponseDto> getActivatedInstructors();
    List<InstructorResponseDto> getDeactivatedInstructors();
}

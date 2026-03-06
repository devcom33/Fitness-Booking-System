package org.heymouad.bookingmanagementsystem.services;

import org.heymouad.bookingmanagementsystem.dtos.client.ClientResponseDto;
import org.heymouad.bookingmanagementsystem.dtos.instructor.InstructorResponseDto;
import org.heymouad.bookingmanagementsystem.enums.UserStatus;

import java.util.List;
import java.util.UUID;

public interface AdminService {
    void updateInstructorStatus(UUID instructorId, UserStatus newStatus);
    void updateAccountState(UUID id, UserStatus status);
    List<InstructorResponseDto> getPendingInstructors();
    List<InstructorResponseDto> getActivatedInstructors();
    List<InstructorResponseDto> getDeactivatedInstructors();

    //client
    void updateClientAccountState(UUID id, UserStatus status);
    List<ClientResponseDto> getActivatedClients();
    List<ClientResponseDto> getDeactivatedClients();
}

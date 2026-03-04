package org.heymouad.bookingmanagementsystem.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.heymouad.bookingmanagementsystem.dtos.UserStatusUpdateRequest;
import org.heymouad.bookingmanagementsystem.dtos.instructor.InstructorResponseDto;
import org.heymouad.bookingmanagementsystem.enums.UserStatus;
import org.heymouad.bookingmanagementsystem.mappers.InstructorMapper;
import org.heymouad.bookingmanagementsystem.services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RequestMapping(path = "/api/v1/admin", produces = "application/json")
@RestController
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final InstructorMapper instructorMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/instructors/{instructorId}/status")
    public ResponseEntity<Void> updateInstructorStatus(@PathVariable UUID instructorId, @RequestBody @Valid UserStatusUpdateRequest userStatusUpdateRequest)
    {
        UserStatus userStatus = UserStatus.valueOf(userStatusUpdateRequest.userStatus());
        adminService.updateInstructorStatus(instructorId, userStatus);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/instructors/pending")
    public ResponseEntity<List<InstructorResponseDto>> getPendingInstructor()
    {
        return ResponseEntity.ok(adminService.getPendingInstructors());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/instructors")
    public ResponseEntity<List<InstructorResponseDto>> getInstructors()
    {
        return ResponseEntity.ok(adminService.getInstructors());
    }

}

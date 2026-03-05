package org.heymouad.bookingmanagementsystem.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.heymouad.bookingmanagementsystem.dtos.UserStatusUpdateRequest;
import org.heymouad.bookingmanagementsystem.dtos.instructor.InstructorResponseDto;
import org.heymouad.bookingmanagementsystem.enums.UserStatus;
import org.heymouad.bookingmanagementsystem.services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RequestMapping(path = "/api/v1/admin/instructors", produces = "application/json")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<List<InstructorResponseDto>> getInstructors(@RequestParam(required = false, defaultValue = "ACTIVE") UserStatus status)
    {
        List<InstructorResponseDto> instructors = switch (status) {
            case PENDING -> adminService.getPendingInstructors();
            case BLOCKED -> adminService.getDeactivatedInstructors();
            default -> adminService.getActivatedInstructors();
        };

        return ResponseEntity.ok(instructors);
    }

    @PatchMapping("/{instructorId}/onboarding")
    public ResponseEntity<Void> approveInstructor(@PathVariable UUID instructorId, @RequestBody @Valid UserStatusUpdateRequest userStatusUpdateRequest)
    {
        UserStatus userStatus = userStatusUpdateRequest.userStatus();
        adminService.updateInstructorStatus(instructorId, userStatus);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/instructors/{instructorId}/access")
    public ResponseEntity<Void> toggleAccountAccess(@PathVariable UUID instructorId, @RequestBody @Valid UserStatusUpdateRequest userStatusUpdateRequest)
    {
        UserStatus userStatus = userStatusUpdateRequest.userStatus();
        adminService.updateAccountState(instructorId, userStatus);
        return ResponseEntity.noContent().build();
    }

}

package org.heymouad.bookingmanagementsystem.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.heymouad.bookingmanagementsystem.dtos.UserStatusUpdateRequest;
import org.heymouad.bookingmanagementsystem.dtos.client.ClientResponseDto;
import org.heymouad.bookingmanagementsystem.dtos.instructor.InstructorResponseDto;
import org.heymouad.bookingmanagementsystem.enums.UserStatus;
import org.heymouad.bookingmanagementsystem.services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping(path = "/api/v1/admin/clients", produces = "application/json")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminClientController {
    private final AdminService adminService;

    @PatchMapping("/{id}/access")
    public ResponseEntity<Void> toggleClientAccess(
            @PathVariable UUID id,
            @RequestBody @Valid UserStatusUpdateRequest request) {
        adminService.updateClientAccountState(id, request.userStatus());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDto>> getClients(@RequestParam(required = false, defaultValue = "ACTIVE") UserStatus status)
    {
        List<ClientResponseDto> clients = (status == UserStatus.BLOCKED) ?
                adminService.getDeactivatedClients() : adminService.getActivatedClients();
        return ResponseEntity.ok(clients);
    }
}

package org.heymouad.bookingmanagementsystem.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.heymouad.bookingmanagementsystem.dtos.UserStatusUpdateRequest;
import org.heymouad.bookingmanagementsystem.dtos.client.ClientResponseDto;
import org.heymouad.bookingmanagementsystem.enums.UserStatus;
import org.heymouad.bookingmanagementsystem.services.AdminClientService;
import org.heymouad.bookingmanagementsystem.services.AdminInstructorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping(path = "/api/v1/admin/clients", produces = "application/json")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Slf4j
public class AdminClientController {
    private final AdminClientService adminClientService;

    @PatchMapping("/{id}/access")
    public ResponseEntity<Void> toggleClientAccess(
            @PathVariable UUID id,
            @RequestBody @Valid UserStatusUpdateRequest request) {
        adminClientService.updateClientAccountState(id, request.userStatus());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDto>> getClients(@RequestParam(required = false, defaultValue = "ACTIVE") UserStatus status)
    {
        log.info("status is {}", status);
        List<ClientResponseDto> clients = (status == UserStatus.BLOCKED) ?
                adminClientService.getDeactivatedClients() : adminClientService.getActivatedClients();
        return ResponseEntity.ok(clients);
    }
}

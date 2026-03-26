package org.heymouad.bookingmanagementsystem.controllers;


import lombok.RequiredArgsConstructor;
import org.heymouad.bookingmanagementsystem.dtos.ClassScheduleResponseDto;
import org.heymouad.bookingmanagementsystem.dtos.admin.AdminClassScheduleResponseDto;
import org.heymouad.bookingmanagementsystem.enums.ScheduleStatus;
import org.heymouad.bookingmanagementsystem.mappers.ClassScheduleMapper;
import org.heymouad.bookingmanagementsystem.services.ClassScheduleService;
import org.heymouad.bookingmanagementsystem.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RequestMapping(path = "/api/v1/admin/schedules", produces = "application/json")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminScheduleController {
    private final ClassScheduleService classScheduleService;
    private final ClassScheduleMapper classScheduleMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<AdminClassScheduleResponseDto>> getAllSchedules(
            @RequestParam(required = false) UUID instructorId,
            @RequestParam(required = false) ScheduleStatus status,
            @PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(classScheduleService.getAllSchedulesForAdmin(instructorId, status, pageable));
    }


    @DeleteMapping("/{id}")
    public void cancelSchedule(@PathVariable UUID id) {
        /* TODO */
    }

    @PatchMapping("/{id}/status")
    public void updateScheduleStatus(@PathVariable UUID id) {
        /* TODO */
    }
}
package org.heymouad.bookingmanagementsystem.controllers;


import lombok.RequiredArgsConstructor;
import org.heymouad.bookingmanagementsystem.dtos.ClassScheduleResponseDto;
import org.heymouad.bookingmanagementsystem.dtos.admin.AdminClassScheduleResponseDto;
import org.heymouad.bookingmanagementsystem.enums.ScheduleStatus;
import org.heymouad.bookingmanagementsystem.mappers.ClassScheduleMapper;
import org.heymouad.bookingmanagementsystem.services.ClassScheduleService;
import org.heymouad.bookingmanagementsystem.services.UserService;
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
    public ResponseEntity<List<AdminClassScheduleResponseDto>> getAllSchedules(
            @RequestParam(required = false) UUID instructorId,
            @RequestParam(required = false) ScheduleStatus status) {

        List<ClassScheduleResponseDto> classScheduleResponseDtoList = classScheduleService.getAllClassSchedules()
                .stream()
                .map(classScheduleMapper::toResponseDto)
                .toList();



        return ResponseEntity.ok(classScheduleResponseDtoList
                .stream()
                .map(cs -> (
                    new AdminClassScheduleResponseDto(
                            cs.id(),
                            cs.fitnessClassDto(),
                            userService.getUserById(cs.instructorDto().userID()).getName(),
                            cs.status(),
                            cs.startTime(),
                            cs.endTime(),
                            cs.templateDto()
                    )
                ))
                .toList()
        );
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
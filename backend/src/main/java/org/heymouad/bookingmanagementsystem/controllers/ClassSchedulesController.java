package org.heymouad.bookingmanagementsystem.controllers;


import lombok.RequiredArgsConstructor;
import org.heymouad.bookingmanagementsystem.dtos.ClassScheduleRequestDto;
import org.heymouad.bookingmanagementsystem.dtos.ClassScheduleResponseDto;
import org.heymouad.bookingmanagementsystem.entities.ClassSchedules;
import org.heymouad.bookingmanagementsystem.mappers.ClassScheduleMapper;
import org.heymouad.bookingmanagementsystem.services.ClassScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping(path="/api/v1/class-schedules", produces = "application/json")
@RestController
@RequiredArgsConstructor
public class ClassSchedulesController {
    private final ClassScheduleService classScheduleService;
    private final ClassScheduleMapper classScheduleMapper;


    @PostMapping
    public ResponseEntity<List<ClassScheduleResponseDto>> createClassSchedules(@RequestBody ClassScheduleRequestDto classScheduleRequestDto)
    {
        ClassSchedules classSchedules = classScheduleMapper.toEntity(classScheduleRequestDto);

        List<ClassScheduleResponseDto> savedClassScheduleRequestDto = classScheduleService.createClassSchedules(classSchedules)
                .stream()
                .map(classScheduleMapper::toResponseDto)
                .toList();

        return ResponseEntity.status(HttpStatus.CREATED).body(savedClassScheduleRequestDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassScheduleResponseDto> getClassScheduleById(@PathVariable UUID id)
    {
        return ResponseEntity.ok(classScheduleMapper.toResponseDto(classScheduleService.getClassScheduleById(id)));
    }

    @GetMapping
    public ResponseEntity<List<ClassScheduleResponseDto>> getAllClassSchedules()
    {
        return ResponseEntity.ok(classScheduleService.getAllClassSchedules()
                .stream()
                .map(classScheduleMapper::toResponseDto)
                .toList());
    }

}

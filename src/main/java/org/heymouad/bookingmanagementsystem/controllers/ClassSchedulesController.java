package org.heymouad.bookingmanagementsystem.controllers;


import lombok.RequiredArgsConstructor;
import org.heymouad.bookingmanagementsystem.dtos.ClassSchedulesDto;
import org.heymouad.bookingmanagementsystem.entities.ClassSchedules;
import org.heymouad.bookingmanagementsystem.mappers.ClassSchedulesMapper;
import org.heymouad.bookingmanagementsystem.services.ClassSchedulesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/class-schedules")
@RestController
@RequiredArgsConstructor
public class ClassSchedulesController {
    private final ClassSchedulesService classSchedulesService;
    private final ClassSchedulesMapper classSchedulesMapper;


    @PostMapping
    public ResponseEntity<List<ClassSchedulesDto>> createClassSchedules(@RequestBody ClassSchedulesDto classSchedulesDto)
    {
        ClassSchedules classSchedules = classSchedulesMapper.toClassSchedules(classSchedulesDto);

        List<ClassSchedulesDto> savedClassSchedulesDto = classSchedulesService.createClassSchedules(classSchedules)
                .stream()
                .map(classSchedulesMapper::toClassSchedulesDto)
                .toList();

        return ResponseEntity.status(HttpStatus.CREATED).body(savedClassSchedulesDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassSchedulesDto> getClassScheduleById(@PathVariable UUID id)
    {
        return ResponseEntity.ok(classSchedulesMapper.toClassSchedulesDto(classSchedulesService.getClassScheduleById(id)));
    }

    @GetMapping
    public ResponseEntity<List<ClassSchedulesDto>> getAllClassSchedules()
    {
        return ResponseEntity.ok(classSchedulesService.getAllClassSchedules()
                .stream()
                .map(classSchedulesMapper::toClassSchedulesDto)
                .toList());
    }

}

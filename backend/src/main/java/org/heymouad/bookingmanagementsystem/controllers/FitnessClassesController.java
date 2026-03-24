package org.heymouad.bookingmanagementsystem.controllers;


import lombok.RequiredArgsConstructor;
import org.heymouad.bookingmanagementsystem.dtos.FitnessClassesDto;
import org.heymouad.bookingmanagementsystem.dtos.FitnessClassesResponseDto;
import org.heymouad.bookingmanagementsystem.entities.FitnessClass;
import org.heymouad.bookingmanagementsystem.mappers.FitnessClassesMapper;
import org.heymouad.bookingmanagementsystem.services.FitnessClassService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping(path = "/api/v1/fitnessClasses", produces = "application/json")
@RestController
@RequiredArgsConstructor
public class FitnessClassesController {
    private final FitnessClassService fitnessClassService;
    private final FitnessClassesMapper fitnessClassesMapper;

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PostMapping
    public ResponseEntity<FitnessClassesResponseDto> createFitnessClass(@RequestBody FitnessClassesDto fitnessClassesDto)
    {
        FitnessClass fitnessClass = fitnessClassesMapper.toFitnessClasses(fitnessClassesDto);
        FitnessClass createdClass = fitnessClassService.createFitnessClasses(fitnessClass);
        FitnessClassesResponseDto fitnessClassesResponseDto = fitnessClassesMapper.toFitnessClassesResponseDto(createdClass);

        return ResponseEntity.status(HttpStatus.CREATED).body(fitnessClassesResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FitnessClassesDto> updateFitnessClass(@PathVariable UUID id, @RequestBody FitnessClassesDto fitnessClassesDto)
    {
        FitnessClass updatedClass = fitnessClassService.updateFitnessClasses(id, fitnessClassesMapper.toFitnessClasses(fitnessClassesDto));

        return ResponseEntity.ok(fitnessClassesMapper.toFitnessClassesDto(updatedClass));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FitnessClassesDto> getFitnessClassById(@PathVariable UUID id)
    {
        FitnessClass fitnessClass = fitnessClassService.getFitnessClassesById(id);

        return ResponseEntity.ok(fitnessClassesMapper.toFitnessClassesDto(fitnessClass));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    @GetMapping
    public ResponseEntity<List<FitnessClassesDto>> getAllFitnessClasses()
    {
        List<FitnessClassesDto> fitnessClassesDtos = fitnessClassService.getAllFitnessClasses()
                .stream()
                .map(fitnessClassesMapper::toFitnessClassesDto)
                .toList();
        return ResponseEntity.ok(fitnessClassesDtos);
    }
}

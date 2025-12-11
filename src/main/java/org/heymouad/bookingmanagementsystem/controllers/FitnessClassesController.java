package org.heymouad.bookingmanagementsystem.controllers;


import lombok.RequiredArgsConstructor;
import org.heymouad.bookingmanagementsystem.dtos.FitnessClassesDto;
import org.heymouad.bookingmanagementsystem.entities.FitnessClasses;
import org.heymouad.bookingmanagementsystem.mappers.FitnessClassesMapper;
import org.heymouad.bookingmanagementsystem.services.FitnessClassesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/fitnessClasses")
@RestController
@RequiredArgsConstructor
public class FitnessClassesController {
    private final FitnessClassesService fitnessClassesService;
    private final FitnessClassesMapper fitnessClassesMapper;

    @PostMapping
    public ResponseEntity<FitnessClassesDto> createFitnessClass(@RequestBody FitnessClassesDto fitnessClassesDto)
    {
        FitnessClasses fitnessClass = fitnessClassesMapper.toFitnessClasses(fitnessClassesDto);
        FitnessClasses createdClass = fitnessClassesService.createFitnessClasses(fitnessClass);
        FitnessClassesDto savedFitnessClassesDto = fitnessClassesMapper.toFitnessClassesDto(createdClass);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedFitnessClassesDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FitnessClassesDto> updateFitnessClass(@PathVariable UUID id, @RequestBody FitnessClassesDto fitnessClassesDto)
    {
        FitnessClasses updatedClass = fitnessClassesService.updateFitnessClasses(id, fitnessClassesMapper.toFitnessClasses(fitnessClassesDto));

        return ResponseEntity.ok(fitnessClassesMapper.toFitnessClassesDto(updatedClass));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FitnessClassesDto> getFitnessClassById(@PathVariable UUID id)
    {
        FitnessClasses fitnessClasses = fitnessClassesService.getFitnessClassesById(id);

        return ResponseEntity.ok(fitnessClassesMapper.toFitnessClassesDto(fitnessClasses));
    }

    @GetMapping
    public ResponseEntity<List<FitnessClassesDto>> getAllFitnessClasses()
    {
        List<FitnessClassesDto> fitnessClassesDtos = fitnessClassesService.getAllFitnessClasses()
                .stream()
                .map(fitnessClassesMapper::toFitnessClassesDto)
                .toList();
        return ResponseEntity.ok(fitnessClassesDtos);
    }
}

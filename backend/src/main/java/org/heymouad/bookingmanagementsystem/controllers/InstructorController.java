package org.heymouad.bookingmanagementsystem.controllers;


import lombok.RequiredArgsConstructor;
import org.heymouad.bookingmanagementsystem.dtos.InstructorDto;
import org.heymouad.bookingmanagementsystem.entities.Instructor;
import org.heymouad.bookingmanagementsystem.mappers.InstructorMapper;
import org.heymouad.bookingmanagementsystem.services.InstructorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/instructors")
@RestController
@RequiredArgsConstructor
public class InstructorController {
    private final InstructorService instructorService;
    private final InstructorMapper instructorMapper;

    @PostMapping
    public ResponseEntity<InstructorDto> createInstructor(@RequestBody InstructorDto instructorDto)
    {
        Instructor instructor = instructorMapper.toEntity(instructorDto);

        Instructor savedInstructor = instructorService.createInstructor(instructor, instructorDto.userID());

        return ResponseEntity.ok(instructorMapper.toDto(savedInstructor));
    }
}

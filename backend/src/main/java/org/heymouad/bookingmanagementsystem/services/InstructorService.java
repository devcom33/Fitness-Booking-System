package org.heymouad.bookingmanagementsystem.services;

import org.heymouad.bookingmanagementsystem.entities.Instructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface InstructorService {
    Instructor createInstructor(Instructor instructor, UUID userId);
}

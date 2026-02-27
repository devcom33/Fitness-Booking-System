package org.heymouad.bookingmanagementsystem.dtos.instructor;


import java.util.UUID;

public record InstructorResponseDto (UUID instructorId, String name, String email, String bio,
                                     String specialization){
}

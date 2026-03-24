package org.heymouad.bookingmanagementsystem.services;

import org.heymouad.bookingmanagementsystem.dtos.UserRegistrationRequestDto;
import org.heymouad.bookingmanagementsystem.dtos.instructor.InstructorApplicationRequest;
import org.heymouad.bookingmanagementsystem.entities.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface UserService {
    User registerClient(UserRegistrationRequestDto registrationRequestDto);
    User applyAsInstructor(InstructorApplicationRequest request);
    User getUserByEmail(String email);
    User getUserById(UUID id);
}

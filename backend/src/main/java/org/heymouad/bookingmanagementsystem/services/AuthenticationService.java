package org.heymouad.bookingmanagementsystem.services;

import org.heymouad.bookingmanagementsystem.dtos.auth.ApplicationResponseDto;
import org.heymouad.bookingmanagementsystem.dtos.auth.AuthRequestDto;
import org.heymouad.bookingmanagementsystem.dtos.auth.AuthResponseDto;
import org.heymouad.bookingmanagementsystem.dtos.UserRegistrationRequestDto;
import org.heymouad.bookingmanagementsystem.dtos.instructor.InstructorApplicationRequest;

public interface AuthenticationService {
    AuthResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto);
    ApplicationResponseDto apply(InstructorApplicationRequest request);
    AuthResponseDto login(AuthRequestDto authRequestDto);
}

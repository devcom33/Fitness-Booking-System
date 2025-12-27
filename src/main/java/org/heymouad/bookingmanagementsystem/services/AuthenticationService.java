package org.heymouad.bookingmanagementsystem.services;

import org.heymouad.bookingmanagementsystem.dtos.auth.AuthRequestDto;
import org.heymouad.bookingmanagementsystem.dtos.auth.AuthResponseDto;
import org.heymouad.bookingmanagementsystem.dtos.UserRegistrationRequestDto;

public interface AuthenticationService {
    AuthResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto);
    AuthResponseDto login(AuthRequestDto authRequestDto);
}

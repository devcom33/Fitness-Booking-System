package org.heymouad.bookingmanagementsystem.services.servicesImpl;

import lombok.RequiredArgsConstructor;
import org.heymouad.bookingmanagementsystem.config.JwtService;
import org.heymouad.bookingmanagementsystem.dtos.auth.ApplicationResponseDto;
import org.heymouad.bookingmanagementsystem.dtos.auth.AuthRequestDto;
import org.heymouad.bookingmanagementsystem.dtos.auth.AuthResponseDto;
import org.heymouad.bookingmanagementsystem.dtos.UserRegistrationRequestDto;
import org.heymouad.bookingmanagementsystem.dtos.instructor.InstructorApplicationRequest;
import org.heymouad.bookingmanagementsystem.entities.Role;
import org.heymouad.bookingmanagementsystem.entities.User;
import org.heymouad.bookingmanagementsystem.enums.UserRole;
import org.heymouad.bookingmanagementsystem.repositories.RoleRepository;
import org.heymouad.bookingmanagementsystem.services.AuthenticationService;
import org.heymouad.bookingmanagementsystem.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto) {

        var user = userService.registerClient(userRegistrationRequestDto);

        var jwtToken = jwtService.generateToken(user);

        return AuthResponseDto.builder().accessToken(jwtToken).build();
    }

    @Override
    public ApplicationResponseDto apply(InstructorApplicationRequest request) {
        userService.applyAsInstructor(request);
        return ApplicationResponseDto.builder()
                .message("Application submitted. Awaiting admin approval.")
                .build();
    }


    @Override
    public AuthResponseDto login(AuthRequestDto requestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.email(), requestDto.password())
        );

        User user = userService.getUserByEmail(requestDto.email());
        var token = jwtService.generateToken(user);

        return AuthResponseDto.builder()
                .accessToken(token)
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().getName())
                .build();
    }
}

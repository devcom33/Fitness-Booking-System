package org.heymouad.bookingmanagementsystem.controllers.Auth;


import lombok.RequiredArgsConstructor;
import org.heymouad.bookingmanagementsystem.dtos.UserRegistrationRequestDto;
import org.heymouad.bookingmanagementsystem.dtos.auth.AuthRequestDto;
import org.heymouad.bookingmanagementsystem.dtos.auth.AuthResponseDto;
import org.heymouad.bookingmanagementsystem.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping(path = "/register", produces = "application/json")
    public ResponseEntity<AuthResponseDto> register(@RequestBody UserRegistrationRequestDto userRegistrationRequestDto)
    {
        return ResponseEntity.ok(authenticationService.register(userRegistrationRequestDto));
    }

    @PostMapping(path = "/login", produces = "application/json")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto)
    {
        return ResponseEntity.ok(authenticationService.login(authRequestDto));
    }
}

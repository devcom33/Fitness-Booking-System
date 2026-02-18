package org.heymouad.bookingmanagementsystem.services.servicesImpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.heymouad.bookingmanagementsystem.dtos.UserRegistrationRequestDto;
import org.heymouad.bookingmanagementsystem.dtos.instructor.InstructorApplicationRequest;
import org.heymouad.bookingmanagementsystem.entities.Instructor;
import org.heymouad.bookingmanagementsystem.entities.Role;
import org.heymouad.bookingmanagementsystem.entities.User;
import org.heymouad.bookingmanagementsystem.enums.UserRole;
import org.heymouad.bookingmanagementsystem.enums.UserStatus;
import org.heymouad.bookingmanagementsystem.repositories.InstructorRepository;
import org.heymouad.bookingmanagementsystem.repositories.RoleRepository;
import org.heymouad.bookingmanagementsystem.repositories.UserRepository;
import org.heymouad.bookingmanagementsystem.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final InstructorRepository instructorRepository;
    private final PasswordEncoder passwordEncoder;


    public User registerClient(UserRegistrationRequestDto registrationRequestDto)
    {
        Role assignedRole = roleRepository.findByName(UserRole.CLIENT).orElseThrow(() ->
                new IllegalStateException("Role not found: " + UserRole.CLIENT)
        );

        var user = User.builder()
                .name(registrationRequestDto.name())
                .email(registrationRequestDto.email())
                .password(passwordEncoder.encode(registrationRequestDto.password()))
                .role(assignedRole)
                .userStatus(UserStatus.ACTIVE)
                .build();

        return userRepository.save(user);
    }

    public User applyAsInstructor(InstructorApplicationRequest request)
    {
        Role assignedRole = roleRepository.findByName(UserRole.INSTRUCTOR).orElseThrow(() ->
                new IllegalStateException("Role not found: " + UserRole.INSTRUCTOR)
        );
        var user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(assignedRole)
                .userStatus(UserStatus.PENDING)
                .build();

        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already registered");
        }

        var savedUser = userRepository.save(user);

        var instructor = Instructor.builder()
                .user(savedUser)
                .bio(request.bio())
                .specialization(request.specialization())
                .build();

        instructorRepository.save(instructor);

        return savedUser;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with email %s not found", email)
                ));
    }
}

package org.heymouad.bookingmanagementsystem.services.servicesImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.heymouad.bookingmanagementsystem.dtos.client.ClientResponseDto;
import org.heymouad.bookingmanagementsystem.dtos.instructor.InstructorResponseDto;
import org.heymouad.bookingmanagementsystem.entities.Instructor;
import org.heymouad.bookingmanagementsystem.entities.Role;
import org.heymouad.bookingmanagementsystem.entities.User;
import org.heymouad.bookingmanagementsystem.enums.UserRole;
import org.heymouad.bookingmanagementsystem.enums.UserStatus;
import org.heymouad.bookingmanagementsystem.exceptions.ResourceNotFoundException;
import org.heymouad.bookingmanagementsystem.repositories.InstructorRepository;
import org.heymouad.bookingmanagementsystem.repositories.RoleRepository;
import org.heymouad.bookingmanagementsystem.repositories.UserRepository;
import org.heymouad.bookingmanagementsystem.services.AdminInstructorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminInstructorServiceImpl implements AdminInstructorService {
    private final InstructorRepository instructorRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional
    @Override
    public void updateInstructorStatus(UUID instructorId, UserStatus newStatus)
    {
        Instructor instructor = instructorRepository.findById(instructorId).orElseThrow(() -> new ResourceNotFoundException("Instructor not found ", instructorId));
        User user = instructor.getUser();

        if (user.getUserStatus() != UserStatus.PENDING) {
            throw new IllegalStateException("Only pending instructors can be updated");
        }

        if (newStatus != UserStatus.ACTIVE && newStatus != UserStatus.REJECTED) {
            throw new IllegalArgumentException("Invalid status transition");
        }

        user.setUserStatus(newStatus);
    }

    @Transactional
    @Override
    public void updateAccountState(UUID instructorId, UserStatus newStatus)
    {
        Instructor instructor = instructorRepository.findById(instructorId).orElseThrow(() -> new ResourceNotFoundException("Instructor not found ", instructorId));
        User user = instructor.getUser();
        UserStatus currentStatus = user.getUserStatus();

        if (currentStatus != UserStatus.ACTIVE && currentStatus != UserStatus.BLOCKED) {
            throw new IllegalStateException("Only active or blocked instructors can be updated. Current status: " + currentStatus);
        }

        if (newStatus != UserStatus.ACTIVE && newStatus != UserStatus.BLOCKED) {
            throw new IllegalArgumentException("Invalid target status for account management: " + newStatus);
        }

        user.setUserStatus(newStatus);
    }

    @Override
    public List<InstructorResponseDto> getPendingInstructors()
    {
        return getInstructorsByStatus(UserStatus.PENDING);
    }

    @Override
    public List<InstructorResponseDto> getDeactivatedInstructors()
    {
        return getInstructorsByStatus(UserStatus.BLOCKED);
    }

    @Override
    public List<InstructorResponseDto> getActivatedInstructors()
    {
        return getInstructorsByStatus(UserStatus.ACTIVE);
    }


    private List<InstructorResponseDto> getInstructorsByStatus(UserStatus status)
    {
        return instructorRepository.findAllByUserStatus(status)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    private InstructorResponseDto mapToResponseDto(Instructor instructor) {
        return new InstructorResponseDto(
                instructor.getId(),
                instructor.getUser().getName(),
                instructor.getUser().getEmail(),
                instructor.getBio(),
                instructor.getSpecialization()
        );
    }
}

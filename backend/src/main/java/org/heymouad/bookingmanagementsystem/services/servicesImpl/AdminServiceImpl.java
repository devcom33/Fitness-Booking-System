package org.heymouad.bookingmanagementsystem.services.servicesImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.heymouad.bookingmanagementsystem.dtos.instructor.InstructorResponseDto;
import org.heymouad.bookingmanagementsystem.entities.Instructor;
import org.heymouad.bookingmanagementsystem.entities.User;
import org.heymouad.bookingmanagementsystem.enums.UserStatus;
import org.heymouad.bookingmanagementsystem.exceptions.ResourceNotFoundException;
import org.heymouad.bookingmanagementsystem.repositories.InstructorRepository;
import org.heymouad.bookingmanagementsystem.services.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {
    private final InstructorRepository instructorRepository;

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

    @Override
    public List<InstructorResponseDto> getPendingInstructors()
    {
        return instructorRepository.findAllByUserStatus(UserStatus.PENDING)
                .stream()
                .map(i -> new InstructorResponseDto(
                        i.getUser().getName(),
                        i.getUser().getEmail(),
                        i.getBio(),
                        i.getSpecialization()
                ))
                .toList();
    }
}

package org.heymouad.bookingmanagementsystem.services.servicesImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.heymouad.bookingmanagementsystem.dtos.client.ClientResponseDto;
import org.heymouad.bookingmanagementsystem.entities.Role;
import org.heymouad.bookingmanagementsystem.entities.User;
import org.heymouad.bookingmanagementsystem.enums.UserRole;
import org.heymouad.bookingmanagementsystem.enums.UserStatus;
import org.heymouad.bookingmanagementsystem.exceptions.ResourceNotFoundException;
import org.heymouad.bookingmanagementsystem.repositories.RoleRepository;
import org.heymouad.bookingmanagementsystem.repositories.UserRepository;
import org.heymouad.bookingmanagementsystem.services.AdminClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminClientServiceImpl implements AdminClientService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional
    @Override
    public void updateClientAccountState(UUID id, UserStatus newStatus) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found ", id));

        if (!(user.getRole().getName() == UserRole.CLIENT))
        {
            throw new IllegalArgumentException("Target user is not a Client. This method only manages Client accounts.");
        }

        UserStatus currentStatus = user.getUserStatus();

        if (currentStatus != UserStatus.ACTIVE && currentStatus != UserStatus.BLOCKED) {
            throw new IllegalStateException("Client is in an invalid state for this action: " + currentStatus);
        }

        if (newStatus != UserStatus.ACTIVE && newStatus != UserStatus.BLOCKED) {
            throw new IllegalArgumentException("Clients can only be set to ACTIVE or BLOCKED. Provided: " + newStatus);
        }

        if (currentStatus == newStatus) {
            log.info("User {} is already {}", id, newStatus);
            return;
        }
        user.setUserStatus(newStatus);
        log.info("Admin changed status for user {} from {} to {}", id, currentStatus, newStatus);
    }

    @Override
    public List<ClientResponseDto> getActivatedClients() {
        Role role = roleRepository.findByName(UserRole.CLIENT).orElseThrow(() -> new ResourceNotFoundException("Role not found : ", String.valueOf(UserRole.CLIENT)));
        return userRepository.findByRoleAndUserStatus(role, UserStatus.ACTIVE).stream().map(c ->
                new ClientResponseDto(
                        c.getId(),
                        c.getName(),
                        c.getEmail()
                )
        ).toList();
    }

    @Override
    public List<ClientResponseDto> getDeactivatedClients() {
        Role role = roleRepository.findByName(UserRole.CLIENT).orElseThrow(() -> new ResourceNotFoundException("Role not found : ", String.valueOf(UserRole.CLIENT)));
        log.info("clients Results is : {}", userRepository.findByRoleAndUserStatus(role, UserStatus.BLOCKED));
        return userRepository.findByRoleAndUserStatus(role, UserStatus.BLOCKED).stream().map(c ->
                new ClientResponseDto(
                        c.getId(),
                        c.getName(),
                        c.getEmail()
                )
        ).toList();
    }
}

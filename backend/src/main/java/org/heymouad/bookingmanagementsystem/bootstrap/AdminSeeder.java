package org.heymouad.bookingmanagementsystem.bootstrap;


import lombok.RequiredArgsConstructor;
import org.heymouad.bookingmanagementsystem.entities.Role;
import org.heymouad.bookingmanagementsystem.entities.User;
import org.heymouad.bookingmanagementsystem.enums.UserRole;
import org.heymouad.bookingmanagementsystem.enums.UserStatus;
import org.heymouad.bookingmanagementsystem.repositories.RoleRepository;
import org.heymouad.bookingmanagementsystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Profile("prod")
@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${ADMIN_EMAIL:admin@system.com}")
    private String adminEmail;

    @Value("${ADMIN_NAME:System Admin}")
    private String adminName;

    @Value("${ADMIN_DEFAULT_PASSWORD}")
    private String adminDefaultPassword;

    @Override
    public void run(String... args) {

        boolean exists = userRepository.findByEmail(adminEmail).isPresent();

        if (exists) return;

        Role adminRole = roleRepository.findByName(UserRole.ADMIN)
                .orElseThrow(() -> new RuntimeException("ADMIN role not found"));

        User admin = User.builder()
                .name(adminName)
                .email(adminEmail)
                .password(passwordEncoder.encode(adminDefaultPassword))
                .role(adminRole)
                .userStatus(UserStatus.ACTIVE)
                .build();

        userRepository.save(admin);
    }
}
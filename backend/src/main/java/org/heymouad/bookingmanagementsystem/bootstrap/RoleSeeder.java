package org.heymouad.bookingmanagementsystem.bootstrap;

import lombok.RequiredArgsConstructor;
import org.heymouad.bookingmanagementsystem.entities.Role;
import org.heymouad.bookingmanagementsystem.enums.UserRole;
import org.heymouad.bookingmanagementsystem.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RoleSeeder implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        if (roleRepository.findByName(UserRole.CLIENT).isEmpty())
            roleRepository.save(new Role(null, UserRole.CLIENT));

        if (roleRepository.findByName(UserRole.ADMIN).isEmpty())
            roleRepository.save(new Role(null, UserRole.ADMIN));

    }
}

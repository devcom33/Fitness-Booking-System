package org.heymouad.bookingmanagementsystem.services;

import org.heymouad.bookingmanagementsystem.entities.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UserService {
    User createUser(User user);
    User getUserById(UUID id);
}

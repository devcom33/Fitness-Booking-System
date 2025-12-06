package org.heymouad.bookingmanagementsystem.exceptions;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, UUID id) {
        super(String.format("%s Not Found with id: %s", resourceName, id));
    }
}

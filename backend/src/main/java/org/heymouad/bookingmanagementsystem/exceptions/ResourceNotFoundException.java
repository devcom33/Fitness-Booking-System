package org.heymouad.bookingmanagementsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, UUID id) {
        super(String.format("%s Not Found with id: %s", resourceName, id));
    }
    public ResourceNotFoundException(String resourceName, String name) {
        super(String.format("%s Not Found with id: %s", resourceName, name));
    }
}

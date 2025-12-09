package org.heymouad.bookingmanagementsystem.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CapacityExceededException extends RuntimeException {
    public CapacityExceededException() {
        super("Capacity has been exceeded");
    }
    public CapacityExceededException(String message) {
        super(message);
    }
}

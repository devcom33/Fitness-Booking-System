package org.heymouad.bookingmanagementsystem.exceptions;


public class CapacityExceededException extends RuntimeException {
    public CapacityExceededException() {
        super("Capacity has been exceeded");
    }
    public CapacityExceededException(String message) {
        super(message);
    }
}

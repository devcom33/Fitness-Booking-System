package org.heymouad.bookingmanagementsystem.exceptions;

public class InstructorBusyException extends RuntimeException {
    public InstructorBusyException(String s) {
        super(String.format(s));
    }
}

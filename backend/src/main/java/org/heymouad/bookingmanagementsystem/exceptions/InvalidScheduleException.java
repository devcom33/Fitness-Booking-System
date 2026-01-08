package org.heymouad.bookingmanagementsystem.exceptions;

public class InvalidScheduleException extends RuntimeException {
    public InvalidScheduleException(String s) {
        super(String.format(s));

    }
}

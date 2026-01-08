package org.heymouad.bookingmanagementsystem.exceptions;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String s) {
        super(String.format(s));
    }
}

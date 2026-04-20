package com.example.workshopsystem.exception;

public class CannotCancelException extends RuntimeException {
    public CannotCancelException(String message) {
        super(message);
    }
}


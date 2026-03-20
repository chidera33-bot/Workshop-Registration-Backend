package com.example.workshopsystem.exception;

public class WorkshopFullException extends RuntimeException {
    public WorkshopFullException(String message) {
        super(message);
    }
}
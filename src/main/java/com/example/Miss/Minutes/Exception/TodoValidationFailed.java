package com.example.Miss.Minutes.Exception;

public class TodoValidationFailed extends RuntimeException {
    public TodoValidationFailed(String message) {
        super(message);
    }
}

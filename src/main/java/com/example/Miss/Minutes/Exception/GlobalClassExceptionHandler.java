package com.example.Miss.Minutes.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalClassExceptionHandler {

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<Map<String , Object>> handlerTodoNotFound(TodoNotFoundException ex){

        Map<String , Object> errorResponse = Map.of(
                "Status", 404,
                "Message", ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(TodoValidationFailed.class)
    public ResponseEntity<Map<String,Object>> HandlerTodoValidationFailed(TodoValidationFailed  ex){


        Map<String , Object> errorResponse = Map.of(
                "Status" , 409,
                "Message", ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorResponse);
    }
}

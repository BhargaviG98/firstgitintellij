package com.example.employee.controlleradvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;


import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {


@ExceptionHandler(IllegalArgumentException.class)
public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Invalid input: " + ex.getMessage());
}

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("That information is not founded in this DB.......... "+ ex.getMessage());
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<?> handleMultipartException(MultipartException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error processing file upload: " + ex.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred: " + ex.getMessage());
    }
}
package com.cigarette.user_service.exception;

import com.cigarette.user_service.dto.ErrorDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleEntityNotFound(EntityNotFoundException e){
        return buildError(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException e){
       List<String> errors = e.getBindingResult()
               .getFieldErrors()
               .stream()
               .map(error -> error.getField() + ": " + error.getDefaultMessage())
               .toList();

       Map<String, Object> response = new HashMap<>();
       response.put("errors", errors);
       response.put("status", HttpStatus.BAD_REQUEST.value());
       response.put("timestamp", LocalDateTime.now());

       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    private ResponseEntity<ErrorDTO> buildError(String message, HttpStatus status){
        return ResponseEntity.status(status).body(
                new ErrorDTO(message, status.value(), LocalDateTime.now()));
    }
}

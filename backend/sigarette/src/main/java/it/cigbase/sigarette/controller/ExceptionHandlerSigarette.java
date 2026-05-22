package it.cigbase.sigarette.controller;

import it.cigbase.sigarette.dto.ErrorDTO;
import it.cigbase.sigarette.exception.IdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class ExceptionHandlerSigarette {

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> idNonTrovato(IdNotFoundException e){
        ErrorDTO error = new ErrorDTO(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}

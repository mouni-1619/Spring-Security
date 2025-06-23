package org.tp.SpringSecurity1Application.Exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.tp.SpringSecurity1Application.Dto.ApiResponce;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobealExceptionHandelear {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponce> handleENFE(EntityNotFoundException exception){
        ApiResponce response=new ApiResponce();
        response.setData(null);
        response.setMessage("Not found");
        response.setError(exception.getMessage());
        response.setStatus(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponce> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiResponce response = new ApiResponce();
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setMessage("Validation failed");
        response.setData(null);
        response.setError(errors);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponce> handleGlobalException(Exception exception) {
        ApiResponce response = new ApiResponce();
        response.setData(null);
        response.setMessage("An error occurred");
        response.setError(exception.getMessage());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

package com.example.orderservice.exceptions;

import com.example.orderservice.dtos.ErrorResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

//@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TokenMissingException.class)
    public ResponseEntity<ErrorResponseDto> handleTokenMissingException(TokenMissingException ex, HttpServletRequest request) {
        ErrorResponseDto errorResponseDto=
                new ErrorResponseDto(ex.getMessage(),HttpStatus.UNAUTHORIZED.value(),new Date(),request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponseDto);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponseDto> handleExpiredJwtException(ExpiredJwtException ex, HttpServletRequest request) {
        ErrorResponseDto errorResponseDto=
                new ErrorResponseDto(ex.getMessage(),HttpStatus.UNAUTHORIZED.value(),new Date(),request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponseDto);
    }
}


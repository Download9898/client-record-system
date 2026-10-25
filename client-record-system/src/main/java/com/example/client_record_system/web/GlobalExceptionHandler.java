package com.example.client_record_system.web;

import com.example.client_record_system.clients.ClientNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.OffsetDateTime;
import java.util.stream.Collectors;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFound(ClientNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return build(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(IllegalArgumentException.class) //Нет обязательного fullName при создании
    public ResponseEntity<ErrorResponseDto> handleIllegalArgument(
            IllegalArgumentException e
    ){
        return build(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class) // Битый JSON, неверный status
    public ResponseEntity<ErrorResponseDto> handleUnreadable(
            HttpMessageNotReadableException e
    ){
        return build(HttpStatus.BAD_REQUEST,"Malformed JSON or invalid field value");
    }

    @ExceptionHandler(NoResourceFoundException.class) //Такого URL нет
    public ResponseEntity<ErrorResponseDto> handleNoResource(NoResourceFoundException ex) {
        return build(HttpStatus.NOT_FOUND, "Resource not found");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class) //URL есть, но метод для него не поддерживается(405, Method 'PATCH' is not supported)
    public ResponseEntity<ErrorResponseDto> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        return build(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class) //Тело пришло не в формате JSON
    public ResponseEntity<ErrorResponseDto> handleUnsupportedMedia(HttpMediaTypeNotSupportedException ex) {
        return build(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getMessage());
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class) //Параметр пути нельзя привести к типу
    public ResponseEntity<ErrorResponseDto> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return build(HttpStatus.BAD_REQUEST,
                "Invalid value '" + ex.getValue() + "' for parameter '" + ex.getName() + "'");
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleOther(
            Exception e
    ){
        log.info("Unexpected error", e);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
    }

    private ResponseEntity<ErrorResponseDto> build(HttpStatus status, String message){
        return ResponseEntity.status(status)
                .body(new ErrorResponseDto(status.value(), message, OffsetDateTime.now()));
    }
}

package com.access.hub.shared.exception;

import com.access.hub.shared.application.dto.ResponseObject;
import com.access.hub.shared.domain.exception.BusinessRuleViolationException;
import com.access.hub.shared.domain.exception.DomainException;
import com.access.hub.shared.domain.exception.NotFoundException;
import com.access.hub.shared.domain.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResponseObject<Object>> handleUnauthorizedException(UnauthorizedException ex) {
        ResponseObject<Object> response = ResponseObject.error(ex.getStatusCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseObject<Object>> handleNotFoundException(NotFoundException ex) {
        ResponseObject<Object> response = ResponseObject.error(ex.getStatusCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(BusinessRuleViolationException.class)
    public ResponseEntity<ResponseObject<Object>> handleBusinessRuleViolation(BusinessRuleViolationException ex) {
        ResponseObject<Object> response = ResponseObject.error(ex.getStatusCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ResponseObject<Object>> handleDomainException(DomainException ex) {
        ResponseObject<Object> response = ResponseObject.error(ex.getStatusCode(), ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseObject<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ResponseObject<Map<String, String>> response = ResponseObject.error(
                HttpStatus.BAD_REQUEST.value(),
                "Dữ liệu đầu vào không hợp lệ!",
                errors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseObject<Object>> handleSystemException(Exception ex) {
        ResponseObject<Object> response = ResponseObject.error(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Hệ thống có sự cố, vui lòng thử lại sau!"
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

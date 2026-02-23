package org.woridge.ms_internal_users_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.woridge.ms_internal_users_management.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthenticatedUserException.class)
    public ResponseEntity<ErrorResponse<String>> handleUnauthenticatedUserException(UnauthenticatedUserException exception) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED) // 401
                .body(ErrorResponse.createErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(NoAuthoritiesFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleNoAuthoritiesFoundException(NoAuthoritiesFoundException exception) {

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN) // 403
                .body(ErrorResponse.createErrorResponse(exception.getMessage()));
    }
}

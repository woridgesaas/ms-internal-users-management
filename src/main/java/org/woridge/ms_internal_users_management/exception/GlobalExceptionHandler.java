package org.woridge.ms_internal_users_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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

    @ExceptionHandler(EmailDuplicatedException.class)
    public ResponseEntity<ErrorResponse<String>> handleEmailDuplicatedException(EmailDuplicatedException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT) //409
                .body(
                        ErrorResponse.createErrorResponse(exception.getMessage())
                );
    }
    @ExceptionHandler(CINDuplicatedException.class)
    public ResponseEntity<ErrorResponse<String>> handleCINDuplicatedException(CINDuplicatedException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT) //409
                .body(
                        ErrorResponse.createErrorResponse(exception.getMessage())
                );
    }
    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleDepartmentNotFoundException(DepartmentNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND) //404
                .body(
                        ErrorResponse.createErrorResponse(exception.getMessage())
                );

    }
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handlePostNotFoundException(PostNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND) //404
                .body(
                        ErrorResponse.createErrorResponse(exception.getMessage())
                );
    }
    @ExceptionHandler(AuthFailedException.class)
    public ResponseEntity<ErrorResponse<String>> handleAuthFailedException(AuthFailedException exception) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE) //503
                .body(
                        ErrorResponse.createErrorResponse(exception.getMessage())
                );
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse<String>> handleAccessDeniedException(AccessDeniedException exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ErrorResponse.createErrorResponse(exception.getMessage()));
    }

}

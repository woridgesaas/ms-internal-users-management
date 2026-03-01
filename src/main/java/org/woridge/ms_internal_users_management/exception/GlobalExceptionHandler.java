package org.woridge.ms_internal_users_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.woridge.ms_internal_users_management.dto.ErrorResponse;

import org.woridge.ms_internal_users_management.exception.holiday.FixedHolidayUpdatedException;
import org.woridge.ms_internal_users_management.exception.holiday.HolidayExistException;
import org.woridge.ms_internal_users_management.exception.holiday.HolidayNotFoundException;
import org.woridge.ms_internal_users_management.exception.holiday.InvalidHolidayDateException;


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
    @ExceptionHandler(DepartmentAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse<String>> handleDepartmentExists(DepartmentAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponse.createErrorResponse(ex.getMessage()));
    }
    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleDepartmentNotFound(
            DepartmentNotFoundException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND) // 404
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

    @ExceptionHandler(HolidayNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleHolidayNotFound(HolidayNotFoundException exception){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.createErrorResponse(exception.getMessage()));
    }
    @ExceptionHandler(HolidayExistException.class)
    public ResponseEntity<ErrorResponse<String>> handleHolidayExist(HolidayExistException exception){
        return  ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponse.createErrorResponse(exception.getMessage()));
    }
    @ExceptionHandler(InvalidHolidayDateException.class)
    public ResponseEntity<ErrorResponse<String>> handleInvalidDate(InvalidHolidayDateException exception){
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(ErrorResponse.createErrorResponse(exception.getMessage()));
    }
    @ExceptionHandler(FixedHolidayUpdatedException.class)
    public ResponseEntity<ErrorResponse<String>> handleFixedHolidayUpdate(FixedHolidayUpdatedException exception) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_CONTENT)  // 422
                .body(ErrorResponse.createErrorResponse(exception.getMessage()));
        }

    @ExceptionHandler(JwtTokenMissingException.class)
    public ResponseEntity<ErrorResponse<String>> JwtTokenMissingException(JwtTokenMissingException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.createErrorResponse(exception.getMessage()));
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> UserNotFoundException(UserNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND) //404
                .body(
                        ErrorResponse.createErrorResponse(exception.getMessage())
                );
    }
    @ExceptionHandler(EducationNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> EducationNotFoundException(EducationNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND) //404
                .body(
                        ErrorResponse.createErrorResponse(exception.getMessage())
                );
    }
    @ExceptionHandler(BankInformationMissingException.class)
    public ResponseEntity<ErrorResponse<String>> BankInformationMissingException(BankInformationMissingException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT) //409
                .body(
                        ErrorResponse.createErrorResponse(exception.getMessage())
                );
    }
    @ExceptionHandler(ProfessionalExperienceNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> ProfessionalExperienceNotFoundException(ProfessionalExperienceNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND) //404
                .body(
                        ErrorResponse.createErrorResponse(exception.getMessage())
                );
    }


}

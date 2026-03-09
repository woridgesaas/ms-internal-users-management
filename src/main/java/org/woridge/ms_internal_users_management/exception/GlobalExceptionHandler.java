package org.woridge.ms_internal_users_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.woridge.ms_internal_users_management.dto.ErrorResponse;
import org.woridge.ms_internal_users_management.enumeration.ErrorCode;
import org.woridge.ms_internal_users_management.exception.post.PostNotFoundException;
import org.woridge.ms_internal_users_management.exception.auth.AuthFailedException;
import org.woridge.ms_internal_users_management.exception.auth.JwtTokenMissingException;
import org.woridge.ms_internal_users_management.exception.auth.NoAuthoritiesFoundException;
import org.woridge.ms_internal_users_management.exception.auth.UnauthenticatedUserException;
import org.woridge.ms_internal_users_management.exception.department.DepartmentAlreadyExistsException;
import org.woridge.ms_internal_users_management.exception.department.DepartmentNotFoundException;
import org.woridge.ms_internal_users_management.exception.holiday.FixedHolidayUpdatedException;
import org.woridge.ms_internal_users_management.exception.holiday.HolidayExistException;
import org.woridge.ms_internal_users_management.exception.holiday.HolidayNotFoundException;
import org.woridge.ms_internal_users_management.exception.holiday.InvalidHolidayDateException;
import org.woridge.ms_internal_users_management.exception.promotion.PromotionException;
import org.woridge.ms_internal_users_management.exception.resignation.ResignationAlreadyExistsException;
import org.woridge.ms_internal_users_management.exception.resignation.ResignationNotFoundException;
import org.woridge.ms_internal_users_management.exception.user.*;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    // ==============================
    // Authentication / Authorization
    // ==============================

    @ExceptionHandler(UnauthenticatedUserException.class)
    public ResponseEntity<ErrorResponse<String>> handleUnauthenticatedUserException(UnauthenticatedUserException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.createErrorResponse(ex.getMessage(), ErrorCode.UNAUTHORIZED));
    }

    @ExceptionHandler(JwtTokenMissingException.class)
    public ResponseEntity<ErrorResponse<String>> handleJwtTokenMissingException(JwtTokenMissingException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.createErrorResponse(ex.getMessage(), ErrorCode.JWT_TOKEN_MISSING));
    }

    @ExceptionHandler(AuthFailedException.class)
    public ResponseEntity<ErrorResponse<String>> handleAuthFailedException(AuthFailedException ex) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ErrorResponse.createErrorResponse(ex.getMessage(), ErrorCode.AUTH_FAILED));
    }

    @ExceptionHandler(NoAuthoritiesFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleNoAuthoritiesFoundException(NoAuthoritiesFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ErrorResponse.createErrorResponse(ex.getMessage(), ErrorCode.NO_AUTHORITIES_FOUND));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse<String>> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ErrorResponse.createErrorResponse(ex.getMessage(), ErrorCode.ACCESS_DENIED));
    }

    // ==============================
    // User
    // ==============================

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.createErrorResponse(ex.getMessage(), ErrorCode.USER_NOT_FOUND));
    }

    @ExceptionHandler(EmailDuplicatedException.class)
    public ResponseEntity<ErrorResponse<String>> handleEmailDuplicatedException(EmailDuplicatedException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponse.createErrorResponse(ex.getMessage(), ErrorCode.EMAIL_DUPLICATED));
    }

    @ExceptionHandler(CINDuplicatedException.class)
    public ResponseEntity<ErrorResponse<String>> handleCINDuplicatedException(CINDuplicatedException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponse.createErrorResponse(ex.getMessage(), ErrorCode.CIN_DUPLICATED));
    }

    @ExceptionHandler(UserOperationException.class)
    public ResponseEntity<ErrorResponse<String>> handleUserOperationException(UserOperationException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponse.createErrorResponse(ex.getMessage(), ErrorCode.USER_OPERATION_FAILED));
    }

    // ==============================
    // Department
    // ==============================

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleDepartmentNotFoundException(DepartmentNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.createErrorResponse(ex.getMessage(), ErrorCode.DEPARTMENT_NOT_FOUND));
    }

    @ExceptionHandler(DepartmentAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse<String>> handleDepartmentExistsException(DepartmentAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponse.createErrorResponse(ex.getMessage(), ErrorCode.DEPARTMENT_ALREADY_EXISTS));
    }

    // ==============================
    // Post
    // ==============================

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handlePostNotFoundException(PostNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.createErrorResponse(ex.getMessage(), ErrorCode.POST_NOT_FOUND));
    }

    // ==============================
    // Holiday
    // ==============================

    @ExceptionHandler(HolidayNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleHolidayNotFound(HolidayNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.createErrorResponse(ex.getMessage(), ErrorCode.HOLIDAY_NOT_FOUND));
    }

    @ExceptionHandler(HolidayExistException.class)
    public ResponseEntity<ErrorResponse<String>> handleHolidayExist(HolidayExistException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponse.createErrorResponse(ex.getMessage(), ErrorCode.HOLIDAY_ALREADY_EXISTS));
    }

    @ExceptionHandler(InvalidHolidayDateException.class)
    public ResponseEntity<ErrorResponse<String>> handleInvalidDate(InvalidHolidayDateException ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ErrorResponse.createErrorResponse(ex.getMessage(), ErrorCode.INVALID_HOLIDAY_DATE));
    }

    @ExceptionHandler(FixedHolidayUpdatedException.class)
    public ResponseEntity<ErrorResponse<String>> handleFixedHolidayUpdate(FixedHolidayUpdatedException ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ErrorResponse.createErrorResponse(ex.getMessage(), ErrorCode.FIXED_HOLIDAY_UPDATE_NOT_ALLOWED));
    }

    // ==============================
    // Education
    // ==============================

    @ExceptionHandler(EducationNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleEducationNotFoundException(EducationNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.createErrorResponse(ex.getMessage(), ErrorCode.EDUCATION_NOT_FOUND));
    }

    // ==============================
    // Bank
    // ==============================

    @ExceptionHandler(BankInformationMissingException.class)
    public ResponseEntity<ErrorResponse<String>> handleBankInformationMissingException(BankInformationMissingException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)//404
                .body(ErrorResponse.createErrorResponse(ex.getMessage(), ErrorCode.BANK_INFORMATION_MISSING));
    }

    // ==============================
    // Experience
    // ==============================

    @ExceptionHandler(ProfessionalExperienceNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleProfessionalExperienceNotFoundException(ProfessionalExperienceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.createErrorResponse(ex.getMessage(), ErrorCode.PROFESSIONAL_EXPERIENCE_NOT_FOUND));
    }




    // ==============================
    // Promotion
    // ==============================

    @ExceptionHandler(PromotionException.class)
    public ResponseEntity<ErrorResponse<String>> handlePromotionException(PromotionException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.createErrorResponse(ex.getMessage(), ErrorCode.PROMOTION_ERROR));
    }


    // ==============================
    // Resignation
    // ==============================

    @ExceptionHandler(ResignationNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleResignationNotFoundException(ResignationNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.createErrorResponse(ex.getMessage(), ErrorCode.RESIGNATION_NOT_FOUND));
    }

    @ExceptionHandler(ResignationAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse<String>> handleResignationAlreadyExistsException(ResignationAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponse.createErrorResponse(ex.getMessage(), ErrorCode.RESIGNATION_ALREADY_EXISTS));
    }

    // ==============================
    // UpdateRequest
    // ==============================
    @ExceptionHandler(NoUserUpdateChangesException.class)
    public  ResponseEntity<ErrorResponse<String>> handleNoUserUpdateChangesException(NoUserUpdateChangesException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.createErrorResponse(ex.getMessage(), ErrorCode.NO_USER_UPDATE_CHANGES));
    }
    @ExceptionHandler(PendingRequestAlreadyExistsException.class)
    public  ResponseEntity<ErrorResponse<String>> handlePendingRequestAlreadyExistsException(PendingRequestAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponse.createErrorResponse(ex.getMessage(), ErrorCode.PENDING_REQUEST_ALREADY_EXISTS));
    }
    @ExceptionHandler(RequestAlreadyProcessedException.class)
    public ResponseEntity<ErrorResponse<String>> handleRequestAlreadyProcessedException(RequestAlreadyProcessedException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponse.createErrorResponse(ex.getMessage(), ErrorCode.REQUEST_ALREADY_PROCESSED));

    }
    @ExceptionHandler(UpdateRequestNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleUpdateRequestNotFoundException(UpdateRequestNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.createErrorResponse(ex.getMessage(), ErrorCode.UPDATE_REQUEST_NOT_FOUND));

    }
    @ExceptionHandler(InvalidRequestStatus.class)
    public ResponseEntity<ErrorResponse<String>> handleInvalidRequestStatus(InvalidRequestStatus ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(ErrorResponse.createErrorResponse(ex.getMessage(), ErrorCode.INVALID_REQUEST_STATUS));

    }

}
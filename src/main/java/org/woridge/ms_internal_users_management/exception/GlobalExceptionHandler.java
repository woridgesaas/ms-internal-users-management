package org.woridge.ms_internal_users_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.woridge.ms_internal_users_management.dto.ErrorResponse;
import org.woridge.ms_internal_users_management.enumeration.ErrorCode;
import org.woridge.ms_internal_users_management.exception.bank_information.*;
import org.woridge.ms_internal_users_management.exception.education.*;
import org.woridge.ms_internal_users_management.exception.post.PostAlreadyExistsException;
import org.woridge.ms_internal_users_management.exception.post.PostIdNotMatchedException;
import org.woridge.ms_internal_users_management.exception.post.PostNameDuplicatedException;
import org.woridge.ms_internal_users_management.exception.post.PostNotFoundException;
import org.woridge.ms_internal_users_management.exception.auth.AuthFailedException;
import org.woridge.ms_internal_users_management.exception.department.DepartmentAlreadyExistsException;
import org.woridge.ms_internal_users_management.exception.department.DepartmentNotFoundException;
import org.woridge.ms_internal_users_management.exception.holiday.FixedHolidayUpdatedException;
import org.woridge.ms_internal_users_management.exception.holiday.HolidayExistException;
import org.woridge.ms_internal_users_management.exception.holiday.HolidayNotFoundException;
import org.woridge.ms_internal_users_management.exception.holiday.InvalidHolidayDateException;
import org.woridge.ms_internal_users_management.exception.professional_experience.*;
import org.woridge.ms_internal_users_management.exception.profile_update_request.PendingProfileUpdateRequestAlreadyExistsException;
import org.woridge.ms_internal_users_management.exception.profile_update_request.ProfileUpdateRequestNotFoundException;
import org.woridge.ms_internal_users_management.exception.profile_update_request.ProfileUpdateRequestNotPendingException;
import org.woridge.ms_internal_users_management.exception.promotion.PromotionIdNotMatchedException;
import org.woridge.ms_internal_users_management.exception.promotion.PromotionNotFoundException;
import org.woridge.ms_internal_users_management.exception.promotion.UserHasNoAssignedPostException;
import org.woridge.ms_internal_users_management.exception.request.MissingCinInRequestHeaderException;
import org.woridge.ms_internal_users_management.exception.request.MissingRoleInRequestHeaderException;
import org.woridge.ms_internal_users_management.exception.resignation.ResignationAlreadyExistsException;
import org.woridge.ms_internal_users_management.exception.resignation.ResignationNotFoundException;
import org.woridge.ms_internal_users_management.exception.salary.SalaryNotFoundException;
import org.woridge.ms_internal_users_management.exception.user.*;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthFailedException.class)
    public ResponseEntity<ErrorResponse<String>> handleAuthFailedException(AuthFailedException exception) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.AUTH_FAILED
                        )
                );
    }

    // User
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleUserNotFoundException(UserNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.USER_NOT_FOUND
                        )
                );
    }

    @ExceptionHandler(EmailDuplicatedException.class)
    public ResponseEntity<ErrorResponse<String>> handleEmailDuplicatedException(EmailDuplicatedException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.EMAIL_DUPLICATED
                        )
                );
    }

    @ExceptionHandler(CinDuplicatedException.class)
    public ResponseEntity<ErrorResponse<String>> handleCINDuplicatedException(CinDuplicatedException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.CIN_DUPLICATED
                        )
                );
    }

    @ExceptionHandler(UserOperationException.class)
    public ResponseEntity<ErrorResponse<String>> handleUserOperationException(UserOperationException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.USER_OPERATION_FAILED
                        )
                );
    }

    // Department
    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleDepartmentNotFoundException(DepartmentNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.DEPARTMENT_NOT_FOUND
                        )
                );
    }

    @ExceptionHandler(DepartmentAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse<String>> handleDepartmentExistsException(DepartmentAlreadyExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.DEPARTMENT_ALREADY_EXISTS
                        )
                );
    }

    // Holiday
    @ExceptionHandler(HolidayNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleHolidayNotFound(HolidayNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.HOLIDAY_NOT_FOUND
                        )
                );
    }

    @ExceptionHandler(HolidayExistException.class)
    public ResponseEntity<ErrorResponse<String>> handleHolidayExist(HolidayExistException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.HOLIDAY_ALREADY_EXISTS
                        )
                );
    }

    @ExceptionHandler(InvalidHolidayDateException.class)
    public ResponseEntity<ErrorResponse<String>> handleInvalidDate(InvalidHolidayDateException exception) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.INVALID_HOLIDAY_DATE));
    }

    @ExceptionHandler(FixedHolidayUpdatedException.class)
    public ResponseEntity<ErrorResponse<String>> handleFixedHolidayUpdate(FixedHolidayUpdatedException exception) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.FIXED_HOLIDAY_UPDATE_NOT_ALLOWED
                        )
                );
    }

    // Education exceptions
    @ExceptionHandler(EducationDoesNotBelongToUserException.class)
    public ResponseEntity<ErrorResponse<String>> handleEducationDoesNotBelongToUserException(EducationDoesNotBelongToUserException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.EDUCATION_DOES_NOT_BELONG_TO_USER
                        )
                );
    }

    @ExceptionHandler(EducationDuplicatedException.class)
    public ResponseEntity<ErrorResponse<String>> handleEducationDuplicatedException(EducationDuplicatedException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.EDUCATION_DUPLICATED
                        )
                );
    }

    @ExceptionHandler(EducationEndDateMissingException.class)
    public ResponseEntity<ErrorResponse<String>> handleEducationEndDateMissingException(EducationEndDateMissingException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.EDUCATION_END_DATE_MISSING
                        )
                );
    }

    @ExceptionHandler(EducationIdNotMatchedException.class)
    public ResponseEntity<ErrorResponse<String>> handleEducationIdNotMatchedException(EducationIdNotMatchedException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.EDUCATION_ID_MISMATCH
                        )
                );
    }

    @ExceptionHandler(EducationNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleEducationNotFoundException(EducationNotFoundException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.EDUCATION_NOT_FOUND
                        )
                );
    }

    @ExceptionHandler(InvalidEducationEndDateException.class)
    public ResponseEntity<ErrorResponse<String>> handleInvalidEducationEndDateException(InvalidEducationEndDateException exception) {

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.INVALID_EDUCATION_END_DATE
                        )
                );
    }

    // Bank information exceptions
    @ExceptionHandler(BankInformationDuplicatedException.class)
    public ResponseEntity<ErrorResponse<String>> handleBankInformationDuplicatedException(BankInformationDuplicatedException exception)
    {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.BANK_INFORMATION_DUPLICATED
                        )
                );
    }

    @ExceptionHandler(BankInformationIdNotMatchedException.class)
    public ResponseEntity<ErrorResponse<String>> handleBankInformationIdNotMatchedException(BankInformationIdNotMatchedException exception)
    {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.BANK_INFORMATION_ID_MISMATCH
                        )
                );
    }

    @ExceptionHandler(BankInformationNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleBankInformationNotFoundException(BankInformationNotFoundException exception)
    {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.BANK_INFORMATION_NOT_FOUND
                        )
                );
    }

    @ExceptionHandler(IbanDuplicatedException.class)
    public ResponseEntity<ErrorResponse<String>> handleIbanDuplicatedException(IbanDuplicatedException exception)
    {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.IBAN_DUPLICATED
                        )
                );
    }

    @ExceptionHandler(RibDuplicatedException.class)
    public ResponseEntity<ErrorResponse<String>> handleRibDuplicatedException(RibDuplicatedException exception)
    {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.RIB_DUPLICATED
                        )
                );
    }

    // Professional experience exceptions
    @ExceptionHandler(CurrentProfessionalExperienceWithEndDateException.class)
    public ResponseEntity<ErrorResponse<String>> handleCurrentProfessionalExperienceWithEndDateException(CurrentProfessionalExperienceWithEndDateException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.CURRENT_PROFESSIONAL_EXPERIENCE_WITH_END_DATE
                        )
                );
    }

    @ExceptionHandler(InvalidProfessionalExperienceEndDateException.class)
    public ResponseEntity<ErrorResponse<String>> handleInvalidProfessionalExperienceEndDateException(InvalidProfessionalExperienceEndDateException exception) {

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.INVALID_PROFESSIONAL_EXPERIENCE_END_DATE
                        )
                );
    }

    @ExceptionHandler(ProfessionalExperienceDoesNotBelongToUserException.class)
    public ResponseEntity<ErrorResponse<String>> handleProfessionalExperienceDoesNotBelongToUserException(ProfessionalExperienceDoesNotBelongToUserException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.PROFESSIONAL_EXPERIENCE_DOES_NOT_BELONG_TO_USER
                        )
                );
    }

    @ExceptionHandler(ProfessionalExperienceDuplicatedException.class)
    public ResponseEntity<ErrorResponse<String>> handleProfessionalExperienceDuplicatedException(ProfessionalExperienceDuplicatedException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.PROFESSIONAL_EXPERIENCE_DUPLICATED
                        )
                );
    }

    @ExceptionHandler(ProfessionalExperienceEndDateMissingException.class)
    public ResponseEntity<ErrorResponse<String>> handleProfessionalExperienceEndDateMissingException(ProfessionalExperienceEndDateMissingException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.PROFESSIONAL_EXPERIENCE_END_DATE_MISSING
                        )
                );
    }

    @ExceptionHandler(ProfessionalExperienceIdNotMatchedException.class)
    public ResponseEntity<ErrorResponse<String>> handleProfessionalExperienceIdNotMatchedException(ProfessionalExperienceIdNotMatchedException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.PROFESSIONAL_EXPERIENCE_ID_MISMATCH
                        )
                );
    }

    @ExceptionHandler(ProfessionalExperienceNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleProfessionalExperienceNotFoundException(ProfessionalExperienceNotFoundException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.PROFESSIONAL_EXPERIENCE_NOT_FOUND
                        )
                );
    }

    // Promotion
    @ExceptionHandler(UserHasNoAssignedPostException.class)
    public ResponseEntity<ErrorResponse<String>> handleUserHasNoAssignedPostException(UserHasNoAssignedPostException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.USER_HAS_NO_ASSIGNED_POST
                        )
                );
    }

    @ExceptionHandler(PromotionNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handlePromotionNotFoundException(PromotionNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.PROMOTION_NOT_FOUND
                        )
                );
    }

    @ExceptionHandler(PromotionIdNotMatchedException.class)
    public ResponseEntity<ErrorResponse<String>> handlePromotionIdNotMatchedException(PromotionIdNotMatchedException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.PROMOTION_ID_MISMATCH
                        )
                );
    }

    // Salary
    @ExceptionHandler(SalaryNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleSalaryNotFoundException(SalaryNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.SALARY_NOT_FOUND
                        )
                );
    }

    // Resignation
    @ExceptionHandler(ResignationNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleResignationNotFoundException(ResignationNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.RESIGNATION_NOT_FOUND
                        )
                );
    }

    @ExceptionHandler(ResignationAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse<String>> handleResignationAlreadyExistsException(ResignationAlreadyExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.RESIGNATION_ALREADY_EXISTS
                        )
                );
    }

    // Profile update request exceptions
    @ExceptionHandler(PendingProfileUpdateRequestAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse<String>> handlePendingProfileUpdateRequestAlreadyExistsException(PendingProfileUpdateRequestAlreadyExistsException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.PENDING_PROFILE_UPDATE_REQUEST_ALREADY_EXISTS
                        )
                );
    }

    @ExceptionHandler(ProfileUpdateRequestNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleProfileUpdateRequestNotFoundException(ProfileUpdateRequestNotFoundException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.PROFILE_UPDATE_REQUEST_NOT_FOUND
                        )
                );

    }

    @ExceptionHandler(ProfileUpdateRequestNotPendingException.class)
    public ResponseEntity<ErrorResponse<String>> handleProfileUpdateRequestNotPendingException(ProfileUpdateRequestNotPendingException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.PROFILE_UPDATE_REQUEST_NOT_PENDING
                        )
                );

    }

    // Post exceptions
    @ExceptionHandler(PostNameDuplicatedException.class)
    public ResponseEntity<ErrorResponse<String>> handlePostNameDuplicatedException(PostNameDuplicatedException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.POST_NAME_DUPLICATED
                        )
                );
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handlePostNotFoundException(PostNotFoundException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.POST_NOT_FOUND
                        )
                );
    }

    @ExceptionHandler(PostAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse<String>> handlePostAlreadyExistsException(PostAlreadyExistsException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.POST_ALREADY_EXISTS
                        )
                );
    }

    @ExceptionHandler(PostIdNotMatchedException.class)
    public ResponseEntity<ErrorResponse<String>> handlePostIdNotMatchedException(PostIdNotMatchedException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.POST_ID_MISMATCH
                        )
                );
    }

    // Request exceptions
    @ExceptionHandler(MissingCinInRequestHeaderException.class)
    public ResponseEntity<ErrorResponse<String>> handleMissingCinInRequestHeaderException(MissingCinInRequestHeaderException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.MISSING_GATEWAY_FORWARDED_HEADER
                        )
                );
    }

    @ExceptionHandler(MissingRoleInRequestHeaderException.class)
    public ResponseEntity<ErrorResponse<String>> handleMissingRoleInRequestHeaderException(MissingRoleInRequestHeaderException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.MISSING_GATEWAY_FORWARDED_HEADER
                        )
                );
    }

    // System exceptions
    // Request validation exception (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<List<String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        List<String> errorList = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(
                        ErrorResponse.createErrorResponse(
                                errorList,
                                ErrorCode.REQUEST_VALIDATION_FAILED
                        )
                );
    }

    // Enum deserialization exception
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(
                        ErrorResponse.createErrorResponse(
                                "Invalid enum value",
                                ErrorCode.INVALID_ENUM_VALUE
                        )
                );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse<String>> handleIllegalArgumentException(IllegalArgumentException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse.createErrorResponse(
                                exception.getMessage(),
                                ErrorCode.INVALID_ARGUMENT
                        )
                );
    }
}
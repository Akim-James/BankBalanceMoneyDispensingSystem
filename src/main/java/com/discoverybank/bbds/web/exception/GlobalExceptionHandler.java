package com.discoverybank.bbds.web.exception;


import com.discoverybank.bbds.exception.NoAccountsToDisplayException;
import com.discoverybank.bbds.exception.WithdrawalException;
import com.discoverybank.bbds.web.model.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * A global exception handler class designed to handle various exceptions
 * thrown by the application and provide appropriate error responses.
 * This class leverages the @RestControllerAdvice annotation to globally
 * manage exceptions across controllers, encapsulating error responses
 * into a standardized format using the ApiErrorResponse record.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles the {@link NoAccountsToDisplayException} thrown when no accounts are available to display.
     * Transforms the exception into a standardized error response.
     *
     * @param ex the exception instance containing details about the error
     * @param request the HTTP request that triggered the exception
     * @return a {@link ResponseEntity} containing an {@link ApiErrorResponse} with the error details and HTTP status NOT_FOUND
     */
    @ExceptionHandler(NoAccountsToDisplayException.class)
    public ResponseEntity<ApiErrorResponse> handleNoAccountsException(
            NoAccountsToDisplayException ex,
            HttpServletRequest request) {

        ApiErrorResponse errorResponse = getApiErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Handles the {@link WithdrawalException} thrown when a withdrawal operation fails.
     * Converts the exception into a standardized error response with relevant details.
     *
     * @param ex the instance of {@link WithdrawalException} containing exception details
     * @param request the HTTP request that triggered the exception
     * @return a {@link ResponseEntity} containing an {@link ApiErrorResponse} with the error details
     */
    @ExceptionHandler(WithdrawalException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiErrorResponse> handleWithdrawalException(WithdrawalException ex, HttpServletRequest request) {

        ApiErrorResponse errorResponse = getApiErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

    }

    /**
     * Constructs an {@link ApiErrorResponse} object with details about an error.
     *
     * @param notFound the HTTP status associated with the error
     * @param ex the error message or details about the exception
     * @param request the HTTP request that triggered the error
     * @return an instance of {@link ApiErrorResponse} containing the timestamp, status, error reason, message, and request URI
     */
    private ApiErrorResponse getApiErrorResponse(HttpStatus notFound, String ex, HttpServletRequest request) {
        return new ApiErrorResponse(
                LocalDateTime.now(),
                notFound.value(),
                notFound.getReasonPhrase(),
                ex,
                request.getRequestURI()
        );
    }

    /**
     * Handles the {@link InvalidDataAccessResourceUsageException} thrown during database interaction
     * when there is an improper use of a resource or syntax that the database cannot understand.
     * Converts the exception into a standardized error response.
     *
     * @param exception the exception instance containing details about the data access error
     * @param request the HTTP request that triggered the exception
     * @return a {@link ResponseEntity} containing an {@link ApiErrorResponse} with the error details
     *         and HTTP status INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidDataAccessResourceUsageException(
            InvalidDataAccessResourceUsageException exception,
            HttpServletRequest request) {

        ApiErrorResponse errorResponse = getApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), request);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

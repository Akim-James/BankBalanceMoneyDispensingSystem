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

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoAccountsToDisplayException.class)
    public ResponseEntity<ApiErrorResponse> handleNoAccountsException(
            NoAccountsToDisplayException ex,
            HttpServletRequest request) {

        ApiErrorResponse errorResponse = getApiErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(WithdrawalException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiErrorResponse> handleWithdrawalException(WithdrawalException ex, HttpServletRequest request) {

        ApiErrorResponse errorResponse = getApiErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

    }

    private ApiErrorResponse getApiErrorResponse(HttpStatus notFound, String ex, HttpServletRequest request) {
        return new ApiErrorResponse(
                LocalDateTime.now(),
                notFound.value(),
                notFound.getReasonPhrase(),
                ex,
                request.getRequestURI()
        );
    }

    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidDataAccessResourceUsageException(
            InvalidDataAccessResourceUsageException exception,
            HttpServletRequest request) {

        ApiErrorResponse errorResponse = getApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), request);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

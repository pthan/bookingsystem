package com.my.bookingsystem.config;

import com.my.bookingsystem.domain.response.ResponseFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Catches any Spring-Security AccessDeniedException (i.e. user is authenticated
     * but lacks the required @PreAuthorize authority) and returns a JSON body.
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseFormat handleAccessDenied(AccessDeniedException ex) {
        return ResponseFormat
                .failedResponse()
                .message("Forbidden – insufficient permissions")
                .data(ex.getMessage())      // optional: include the exception text
                .build();
    }

    /**
     * (Optional) Catch any other uncaught exceptions and return a 500 JSON body.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseFormat handleAll(Exception ex) {
        return ResponseFormat
                .failedResponse()
                .message("An unexpected error occurred")
                .data(ex.getMessage())
                .build();
    }
}

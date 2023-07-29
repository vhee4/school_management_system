package com.SchoolManagement.School.Management.System.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex) {
        // Create a response body object with the desired JSON structure
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("error", "Unauthorized");
        responseBody.put("message", ex.getMessage());

        // Create the response entity with the JSON body and appropriate HTTP status code
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }
}

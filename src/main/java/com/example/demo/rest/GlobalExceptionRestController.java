package com.example.demo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(basePackages = "com.example.demo.rest")
public class GlobalExceptionRestController /*extends ResponseEntityExceptionHandler*/ {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> exceptionHandler(Exception exception, HttpServletRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("path", request.getRequestURI());
        response.put("status", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
        response.put("message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

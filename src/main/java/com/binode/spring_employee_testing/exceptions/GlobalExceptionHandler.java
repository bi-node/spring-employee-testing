package com.binode.spring_employee_testing.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<?> handleEmployeeNotFoundException(EmployeeNotFoundException e,
                                                             HttpServletRequest request,
    HandlerMethod method) {
        String uri = request.getRequestURI();
        String methodName=method.getMethod().getName();
        String time= ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return new ResponseEntity<>(e.getMessage()+uri+methodName+time, HttpStatus.NOT_FOUND);
    }



    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<?> handleDepartmentNotFound(DepartmentNotFoundException e,
                                                      HttpServletRequest request,
                                                        HandlerMethod method) {
        String uri = request.getRequestURI();
        String methodName=method.getMethod().getName();
        String time= ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return new ResponseEntity<>(e.getMessage()+uri+methodName+time, HttpStatus.NOT_FOUND);

    }
}

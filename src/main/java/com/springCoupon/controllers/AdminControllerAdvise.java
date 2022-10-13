package com.springCoupon.controllers;

import com.springCoupon.exception.CouponSystemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RestController
public class AdminControllerAdvise {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorDetails> handle(Exception e) {
        ErrorDetails error = new ErrorDetails("Custom Error", e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {CouponSystemException.class})
    public ResponseEntity<ErrorDetails> handleCustomException(CouponSystemException e) {
        ErrorDetails error = new ErrorDetails("Custom Error", e.getException());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}

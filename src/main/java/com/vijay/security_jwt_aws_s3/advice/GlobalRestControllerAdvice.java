package com.vijay.security_jwt_aws_s3.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vijay.security_jwt_aws_s3.exception.ResourceNotFound;


@RestControllerAdvice
public class GlobalRestControllerAdvice {

     @ExceptionHandler(ResourceNotFound.class)
     public ResponseEntity<String> handleResourceNotFound(ResourceNotFound ex) {
          return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
     }

}

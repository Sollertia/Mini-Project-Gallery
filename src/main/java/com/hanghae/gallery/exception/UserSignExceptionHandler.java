package com.hanghae.gallery.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserSignExceptionHandler {

    @ExceptionHandler(value = {UserSignException.class})
    public ResponseEntity<Object> handleUserRequestException(UserSignException ex){
        UserException userException = new UserException(ex.getMessage(), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(userException,HttpStatus.BAD_REQUEST);
    }

}
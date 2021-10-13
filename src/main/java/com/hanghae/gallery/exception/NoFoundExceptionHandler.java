package com.hanghae.gallery.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class NoFoundExceptionHandler {

    @ExceptionHandler(value = {NoFoundException.class})
    public ResponseEntity<Object> handleUserRequestException(NoFoundException ex){
//        UserException userException = new UserException(ex.getMessage(), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(ex,HttpStatus.BAD_REQUEST);
    }
}

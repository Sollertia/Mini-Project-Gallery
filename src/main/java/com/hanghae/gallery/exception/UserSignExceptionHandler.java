package com.hanghae.gallery.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
// 클라이언트 요청의 모든 예외처리를 찾음
@RestControllerAdvice
public class UserSignExceptionHandler {
    // ExceptionHandler의 밸류 값은 UserSignException의 클래스
    @ExceptionHandler(value = {UserSignException.class})
    // ResponseEntity의 오브젝트 빈에 UserSignException의 예외를 건다.
    public ResponseEntity<Object> handleUserRequestException(UserSignException ex){
        // UserException 클래스에 어노테이션 빈의 메시지와 httpStatus를 담아서
        UserException userException = new UserException(ex.getMessage(), HttpStatus.BAD_REQUEST);
        // Userexception과 httpStatus를 넘겨줌
        return new ResponseEntity<>(userException,HttpStatus.BAD_REQUEST);
    }

}
package com.hanghae.gallery.exception;

public class UserSignException extends IllegalArgumentException{
    // UserSignException에 스트링과 수퍼메시지를 담음
    public UserSignException(String message){super(message);}
}
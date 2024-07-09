package com.codewithanurag.authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(UserExistException.class)
    protected ApiErrorResponse handleUserExistException(UserExistException userExistException) {
        return ApiErrorResponse.builder().message(userExistException.getMessage()).code(HttpStatus.NOT_ACCEPTABLE.value()).build();
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    protected ApiErrorResponse handleUserExistException(UsernameNotFoundException notFoundException) {
        return ApiErrorResponse.builder().message(notFoundException.getMessage()).code(HttpStatus.NOT_FOUND.value()).build();
    }
}

package com.codewithanurag.authentication.exception;

public class UserExistException extends RuntimeException {
    public UserExistException(String userName) {
        super(userName);
    }
}

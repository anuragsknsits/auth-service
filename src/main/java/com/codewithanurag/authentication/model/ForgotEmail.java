package com.codewithanurag.authentication.model;

public record ForgotEmail(String email) {
    public ForgotEmail {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }
    }
}

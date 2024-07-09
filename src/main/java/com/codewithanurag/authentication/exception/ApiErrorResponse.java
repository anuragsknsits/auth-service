package com.codewithanurag.authentication.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiErrorResponse {
    private Integer code;
    private String message;
}

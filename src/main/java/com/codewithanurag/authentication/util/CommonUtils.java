package com.codewithanurag.authentication.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.Optional;

public class CommonUtils {

    public static Optional<String> getValueFromCookies(HttpServletRequest request, String key) {
        return Optional.ofNullable(request.getCookies())
                .flatMap(cookies -> Arrays.stream(cookies)
                        .filter(cookie -> key.equals(cookie.getName()))
                        .map(Cookie::getValue)
                        .findFirst());
    }
}

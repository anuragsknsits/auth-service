package com.codewithanurag.authentication.controller;

import com.codewithanurag.authentication.model.AuthResponse;
import com.codewithanurag.authentication.model.AuthenticationRequest;
import com.codewithanurag.authentication.model.ChangePassword;
import com.codewithanurag.authentication.model.SignUp;
import com.codewithanurag.authentication.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody SignUp registerRequest) {
        userService.registerUser(registerRequest);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) {
        String token = userService.authenticateUser(authenticationRequest);
        setJwtCookie(response, token);
        return ResponseEntity.ok(new AuthResponse(authenticationRequest.getEmailId(), token));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Optional.ofNullable(request.getCookies())
                .flatMap(cookies -> Arrays.stream(cookies).filter(cookie -> "authToken".equals(cookie.getName())).findFirst())
                .ifPresent(cookie -> {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    cookie.setHttpOnly(true);
                    cookie.setSecure(true);
                    response.addCookie(cookie);
                });

        request.getSession().invalidate(); // Invalidate session
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/verifyToken")
    public ResponseEntity<String> verifyToken(HttpServletRequest request) {
        return getAuthTokenFromCookies(request)
                .map(userService::getUserName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().body("Invalid token"));
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePassword changePassword, HttpServletRequest request, HttpServletResponse response) {
        return getAuthTokenFromCookies(request)
                .map(userService::getUserName)
                .map(userName -> {
                    String newToken = userService.changePassword(userName, changePassword);
                    setJwtCookie(response, newToken);
                    return ResponseEntity.ok("Password changed successfully");
                })
                .orElse(ResponseEntity.status(401).body("Unauthorized request"));
    }

    private void setJwtCookie(HttpServletResponse response, String token) {
        response.addHeader("Set-Cookie", "authToken=" + token + "; Path=/; HttpOnly; Secure; SameSite=Strict");
    }

    private Optional<String> getAuthTokenFromCookies(HttpServletRequest request) {
        return Optional.ofNullable(request.getCookies())
                .flatMap(cookies -> Arrays.stream(cookies)
                        .filter(cookie -> "authToken".equals(cookie.getName()))
                        .map(Cookie::getValue)
                        .findFirst());
    }
}

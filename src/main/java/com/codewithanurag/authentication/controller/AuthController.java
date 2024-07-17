package com.codewithanurag.authentication.controller;

import com.codewithanurag.authentication.model.AuthResponse;
import com.codewithanurag.authentication.model.AuthenticationRequest;
import com.codewithanurag.authentication.model.SignUp;
import com.codewithanurag.authentication.service.impl.AuthServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Optional;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthServiceImpl authService;

    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AuthResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) {

        String token = authService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        Cookie jwtCookie = new Cookie("jwt", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(24 * 60 * 60);
        response.addCookie(jwtCookie);

        return new AuthResponse(authenticationRequest.getUsername(), token);
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createAuthenticationToken(@RequestBody SignUp signUp) {
        return authService.signUp(signUp);
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate JWT Token (optional: depends on your strategy)
        // Clear JWT cookie
        for (Cookie jwtCookie : request.getCookies()) {
            jwtCookie.setValue(null);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(0); // Delete the cookie
            response.addCookie(jwtCookie);
        }
        // Invalidate session (if applicable)
        request.getSession().invalidate();

        return "Logged out successfully";
    }

    @GetMapping("/verifyToken")
    public String verifyToken(HttpServletRequest request) {
        Optional<Cookie> optionalCookie = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("jwt")).findFirst();
        String token = optionalCookie.map(Cookie::getValue).orElse(null);
        return authService.getUserName(token);
    }
}

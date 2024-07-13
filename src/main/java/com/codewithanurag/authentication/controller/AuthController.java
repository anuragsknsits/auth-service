package com.codewithanurag.authentication.controller;

import com.codewithanurag.authentication.model.AuthenticationRequest;
import com.codewithanurag.authentication.model.SignUp;
import com.codewithanurag.authentication.service.impl.AuthServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthServiceImpl authService;

    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response, HttpServletRequest request) {

        String token = authService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        Cookie jwtCookie = new Cookie("jwt", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(24 * 60 * 60);
        response.addCookie(jwtCookie);

        return "Logged in successfully";
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createAuthenticationToken(@RequestBody SignUp signUp) {
        return authService.signUp(signUp);
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate JWT Token (optional: depends on your strategy)
        // Clear JWT cookie
        Cookie jwtCookie = new Cookie("jwt", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0); // Delete the cookie
        response.addCookie(jwtCookie);

        // Invalidate session (if applicable)
        request.getSession().invalidate();

        return "Logged out successfully";
    }


    @GetMapping("/csrf-token")
    public CsrfToken csrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute(CsrfToken.class.getName());
    }
}

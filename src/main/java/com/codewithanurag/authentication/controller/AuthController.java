package com.codewithanurag.authentication.controller;

import com.codewithanurag.authentication.model.AuthenticationRequest;
import com.codewithanurag.authentication.model.SignUp;
import com.codewithanurag.authentication.service.impl.AuthServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthServiceImpl authService;

    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        return authService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword());
    }

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createAuthenticationToken(@RequestBody SignUp signUp) {
        return authService.signUp(signUp);
    }
}

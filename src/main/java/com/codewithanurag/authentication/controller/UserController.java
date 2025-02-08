package com.codewithanurag.authentication.controller;

import com.codewithanurag.authentication.model.AuthResponse;
import com.codewithanurag.authentication.model.AuthenticationRequest;
import com.codewithanurag.authentication.model.ChangePassword;
import com.codewithanurag.authentication.model.SignUp;
import com.codewithanurag.authentication.service.UserService;
import com.codewithanurag.authentication.util.CommonUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
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
        Pair<String, AuthResponse> responsePair = userService.authenticateUser(authenticationRequest);
        setJwtCookie(response, responsePair.getFirst());
        return ResponseEntity.ok(responsePair.getSecond());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        List<String> cookiesToDelete = List.of("JSESSIONID", "authToken", "XSRF-TOKEN");
        Optional.ofNullable(request.getCookies()).ifPresent(cookies -> {
            Arrays.stream(cookies).filter(cookie -> cookiesToDelete.contains(cookie.getName()))
                    .forEach(cookie -> {
                        cookie.setValue(null);
                        cookie.setMaxAge(0);
                        cookie.setPath("/");
                        cookie.setHttpOnly(true);
                        cookie.setSecure(true);
                        response.addCookie(cookie);
                    });
        });

        request.getSession().invalidate(); // Invalidate session
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/verifyToken")
    public ResponseEntity<AuthResponse> verifyToken(HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        String email = CommonUtils.getValueFromCookies(request, "authToken")
                .map(userService::getUserName).orElseThrow(() -> new RuntimeException("Invalid Token"));
        String role = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElse(null);
        return ResponseEntity.ok(new AuthResponse("", email, role));
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePassword changePassword, @AuthenticationPrincipal UserDetails userDetails,
                                                 HttpServletResponse response) {
        return Optional.of(userDetails.getUsername())
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
}

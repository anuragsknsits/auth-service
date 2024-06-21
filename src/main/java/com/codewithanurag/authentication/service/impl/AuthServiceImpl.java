package com.codewithanurag.authentication.service.impl;

import com.codewithanurag.authentication.entity.UserEntity;
import com.codewithanurag.authentication.model.SignUp;
import com.codewithanurag.authentication.repository.UserRepository;
import com.codewithanurag.authentication.service.AuthService;
import com.codewithanurag.authentication.util.JWTUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JWTUtil jwtUtil, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public String signUp(SignUp signUp) {
        UserEntity userEntity = userRepository.save(UserEntity.builder().userName(signUp.getUsername())
                .password(signUp.getPassword()).role(signUp.getRole()).build());
        return userEntity.getUserName();
    }

    @Override
    public String login(String userName, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        } catch (AuthenticationException e) {
            throw new RuntimeException("Incorrect UserDetails", e);
        }
        return generateAuthToken(userName, password);
    }

    private String generateAuthToken(String userName, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            return jwtUtil.generateToken(userDetails);
        }
        return null;
    }
}

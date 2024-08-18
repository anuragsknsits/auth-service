package com.codewithanurag.authentication.service.impl;

import com.codewithanurag.authentication.model.SignUp;
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

    //    private final UserEntityRepository userEntityRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public AuthServiceImpl(/*UserEntityRepository userEntityRepository,*/ AuthenticationManager authenticationManager, JWTUtil jwtUtil, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
//        this.userEntityRepository = userEntityRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public String signUp(SignUp signUp) {

//        userEntityRepository.findByEmailId(signUp.getEmailId()).ifPresent(userEntity -> {
//            throw new UserExistException(" User name " + signUp.getEmailId() + " already exists Please try another username");
//        });
//
//        UserEntity userEntity = userEntityRepository.save(
//                UserEntity.builder()
//                        .firstName(signUp.getFirstName())
//                        .lastName(signUp.getLastName())
//                        .emailId(signUp.getEmailId())
//                        .password(signUp.getPassword()).role(signUp.getRole()).build());
//        return userEntity.getFirstName();
        return "";
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

    public String getUserName(String token) {
        return jwtUtil.extractUserName(token);
    }
}

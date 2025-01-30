package com.codewithanurag.authentication.service;

import com.codewithanurag.authentication.model.AuthenticationRequest;
import com.codewithanurag.authentication.model.ChangePassword;
import com.codewithanurag.authentication.model.SignUp;

public interface UserService {
    void registerUser(SignUp signUp);

    String authenticateUser(AuthenticationRequest authenticationRequest);

    String getUserName(String token);

    void updateUser(Long userId, SignUp signUp);

    void deleteUser(Long userId);

    String changePassword(String userName, ChangePassword changePassword);
}

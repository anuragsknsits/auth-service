package com.codewithanurag.authentication.service;

import com.codewithanurag.authentication.model.SignUp;

public interface AuthService {
    String signUp(SignUp signUp);

    String login(String userName, String password);
}

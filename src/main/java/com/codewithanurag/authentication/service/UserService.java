package com.codewithanurag.authentication.service;

import com.codewithanurag.authentication.entity.UserProfile;
import com.codewithanurag.authentication.model.AuthResponse;
import com.codewithanurag.authentication.model.AuthenticationRequest;
import com.codewithanurag.authentication.model.ChangePassword;
import com.codewithanurag.authentication.model.Profile;
import com.codewithanurag.authentication.model.SignUp;
import org.springframework.data.util.Pair;

public interface UserService {
    void registerUser(SignUp signUp);

    Pair<String, AuthResponse> authenticateUser(AuthenticationRequest authenticationRequest);

    String getUserName(String token);

    void updateUser(Long userId, SignUp signUp);

    void deleteUser(Long userId);

    String changePassword(String userName, ChangePassword changePassword);

    UserProfile getUserProfile(String username);

    UserProfile updateUserProfile(String username, Profile updatedProfile);
}

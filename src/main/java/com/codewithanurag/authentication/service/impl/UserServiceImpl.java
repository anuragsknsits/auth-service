package com.codewithanurag.authentication.service.impl;

import com.codewithanurag.authentication.entity.Role;
import com.codewithanurag.authentication.entity.UserDTO;
import com.codewithanurag.authentication.entity.UserProfile;
import com.codewithanurag.authentication.model.AuthResponse;
import com.codewithanurag.authentication.model.AuthenticationRequest;
import com.codewithanurag.authentication.model.ChangePassword;
import com.codewithanurag.authentication.model.Profile;
import com.codewithanurag.authentication.model.SignUp;
import com.codewithanurag.authentication.repository.RoleRepository;
import com.codewithanurag.authentication.repository.UserProfileRepository;
import com.codewithanurag.authentication.repository.UserRepository;
import com.codewithanurag.authentication.service.UserService;
import com.codewithanurag.authentication.util.JWTUtil;
import org.springframework.data.util.Pair;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository,
                           UserProfileRepository userProfileRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void registerUser(SignUp signUp) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(signUp.getEmailId());
        userDTO.setPassword(passwordEncoder.encode(signUp.getPassword()));
        userDTO.setActive(true);

        if (userDTO.getRole() == null) {
            List<Role> roleList = roleRepository.findAll();
            Role role = roleList.stream().filter(userRole -> userRole.getName().equals("ADMIN")).findFirst().orElse(null);

            if (role == null) {
                throw new RuntimeException("Role not found!");
            }
            userDTO.setRole(role);
        }
        userRepository.save(userDTO);
    }

    @Override
    public Pair<String, AuthResponse> authenticateUser(AuthenticationRequest authenticationRequest) {
        UserDTO userDTO = userRepository.findByEmail(authenticationRequest.getEmailId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (passwordEncoder.matches(authenticationRequest.getPassword(), userDTO.getPassword())) {
            String token = jwtUtil.generateToken(userDTO.getEmail());
            String userName = userDTO.getUserProfile() != null ? userDTO.getUserProfile().getFirstName() : null;
            AuthResponse response = new AuthResponse(
                    userName,
                    userDTO.getEmail(),
                    userDTO.getRole().getName()
            );
            return Pair.of(token, response);
        }

        throw new RuntimeException("Invalid credentials");
    }

    @Override
    public String getUserName(String token) {
        return jwtUtil.extractUserName(token);
    }

    @Override
    public void updateUser(Long userId, SignUp signUp) {
        UserDTO userDTO = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userDTO.setEmail(signUp.getEmailId());
        userDTO.setPassword(passwordEncoder.encode(signUp.getPassword()));

        /*Role role = roleRepository.findById(signUp.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        userDTO.setRole(role);*/

        userRepository.save(userDTO);
    }

    @Override
    public void deleteUser(Long userId) {
        UserDTO userDTO = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        userDTO.setActive(false);
        userRepository.save(userDTO);
    }

    @Override
    public String changePassword(String userName, ChangePassword changePassword) {
        UserDTO userDTO = userRepository.findByEmail(userName).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (passwordEncoder.matches(changePassword.oldPassword(), userDTO.getPassword())) {
            userDTO.setPassword(passwordEncoder.encode(changePassword.newPassword()));
            userRepository.save(userDTO);
            return jwtUtil.generateToken(userDTO.getEmail());
        }

        throw new RuntimeException("Invalid credentials");
    }

    @Override
    public Profile getUserProfile(String email) {
        UserDTO user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserProfile userProfile = userProfileRepository.findByUser(user).orElse(new UserProfile());
        return Profile.builder().address(userProfile.getAddress()).phoneNumber(userProfile.getPhoneNumber())
                .panCard(userProfile.getPanCard()).firstName(userProfile.getFirstName()).lastName(userProfile.getLastName()).build();
    }

    @Override
    public UserProfile updateUserProfile(String email, Profile updatedProfile) {
        UserDTO user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserProfile profile = userProfileRepository.findByUser(user).orElse(new UserProfile());

        if (profile.getUser() == null) {
            profile.setUser(user);
        }

        profile.setFirstName(updatedProfile.getFirstName());
        profile.setLastName(updatedProfile.getLastName());
        profile.setPhoneNumber(updatedProfile.getPhoneNumber());
        profile.setPanCard(updatedProfile.getPanCard());
        profile.setAddress(updatedProfile.getAddress());

        return userProfileRepository.save(profile);
    }

    @Override
    public void forgetPassword(String email) {
        UserDTO userDTO = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        //send email to personal mail
        userDTO.setPassword(passwordEncoder.encode("Qwerty@123"));
        userDTO.setActive(true);

        if (userDTO.getRole() == null) {
            List<Role> roleList = roleRepository.findAll();
            Role role = roleList.stream().filter(userRole -> userRole.getName().equals("ADMIN")).findFirst().orElse(null);

            if (role == null) {
                throw new RuntimeException("Role not found!");
            }
            userDTO.setRole(role);
        }
        userRepository.save(userDTO);
    }
}

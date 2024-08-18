package com.codewithanurag.authentication.service.impl;

import com.codewithanurag.authentication.entity.Role;
import com.codewithanurag.authentication.entity.User;
import com.codewithanurag.authentication.model.AuthenticationRequest;
import com.codewithanurag.authentication.model.SignUp;
import com.codewithanurag.authentication.repository.EmployeeRoleAssignmentRepository;
import com.codewithanurag.authentication.repository.RoleRepository;
import com.codewithanurag.authentication.repository.UserRepository;
import com.codewithanurag.authentication.service.UserService;
import com.codewithanurag.authentication.util.JWTUtil;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;
    private final EmployeeRoleAssignmentRepository employeeRoleAssignmentRepository;

    private final JWTUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, EmployeeRoleAssignmentRepository employeeRoleAssignmentRepository, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.employeeRoleAssignmentRepository = employeeRoleAssignmentRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void registerUser(SignUp signUp) {
        User user = new User();
        user.setEmail(signUp.getEmailId());
        user.setPassword(passwordEncoder.encode(signUp.getPassword()));
        user.setFirstName(signUp.getFirstName());
        user.setLastName(signUp.getLastName());
        user.setActive(true);

        Role role = roleRepository.findByName(signUp.getRole()).orElseThrow(() -> new RuntimeException("Role not found"));

        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public String authenticateUser(AuthenticationRequest authenticationRequest) {
        User user = userRepository.findByEmail(authenticationRequest.getEmailId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (user != null && passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
            return jwtUtil.generateToken(user.getEmail());
        }
        throw new RuntimeException("Invalid credentials");
    }


    @Override
    public String getUserName(String token) {
        return jwtUtil.extractUserName(token);
    }

    @Override
    public void updateUser(Long userId, SignUp signUp) {
        // Find existing user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Find role based on roleId
        Role role = roleRepository.findByName(signUp.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // Update user details
        user.setEmail(signUp.getEmailId());
        user.setPassword(passwordEncoder.encode(signUp.getPassword()));
        user.setFirstName(signUp.getFirstName());
        user.setLastName(signUp.getLastName());
        user.setRole(role);

        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        // Find existing user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Make user inactive
        user.setActive(false);

        // Optionally: Remove user from role assignments (if applicable)
        employeeRoleAssignmentRepository.deleteByUserId(userId); // Assuming this method exists

        // Save the updated user entity
        userRepository.save(user);
    }
}


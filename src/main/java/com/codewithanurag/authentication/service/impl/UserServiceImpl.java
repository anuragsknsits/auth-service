package com.codewithanurag.authentication.service.impl;

import com.codewithanurag.authentication.entity.Role;
import com.codewithanurag.authentication.entity.UserDTO;
import com.codewithanurag.authentication.model.AuthenticationRequest;
import com.codewithanurag.authentication.model.ChangePassword;
import com.codewithanurag.authentication.model.SignUp;
import com.codewithanurag.authentication.repository.RoleRepository;
import com.codewithanurag.authentication.repository.UserRepository;
import com.codewithanurag.authentication.service.UserService;
import com.codewithanurag.authentication.util.JWTUtil;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void registerUser(SignUp signUp) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(signUp.getEmailId());
        userDTO.setPassword(passwordEncoder.encode(signUp.getPassword()));
        userDTO.setFirstName("");
        userDTO.setLastName("");
        userDTO.setActive(true);

        List<Role> roleList = roleRepository.findAll();
        Role role = roleList.stream().filter(userRole -> userRole.getName().equals("ADMIN")).findFirst().orElse(null);

        userDTO.setRole(role);
        userRepository.save(userDTO);
    }

    @Override
    public String authenticateUser(AuthenticationRequest authenticationRequest) {
        UserDTO userDTO = userRepository.findByEmail(authenticationRequest.getEmailId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (userDTO != null && passwordEncoder.matches(authenticationRequest.getPassword(), userDTO.getPassword())) {
            return jwtUtil.generateToken(userDTO.getEmail());
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
        UserDTO userDTO = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Update user details
        userDTO.setEmail(signUp.getEmailId());
        userDTO.setPassword(passwordEncoder.encode(signUp.getPassword()));

        // Find role based on roleId
        /*Role role = roleRepository.findByName(signUp.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        userDTO.setFirstName(signUp.getFirstName());
        userDTO.setLastName(signUp.getLastName());
        userDTO.setRole(role);*/

        userRepository.save(userDTO);
    }

    @Override
    public void deleteUser(Long userId) {
        // Find existing user
        UserDTO userDTO = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Make user inactive
        userDTO.setActive(false);

        // Optionally: Remove user from role assignments (if applicable)
//        employeeRoleAssignmentRepository.deleteByUserDTO_Id(userId); // Assuming this method exists

        // Save the updated user entity
        userRepository.save(userDTO);
    }

    @Override
    public String changePassword(String userName, ChangePassword changePassword) {
        UserDTO userDTO = userRepository.findByEmail(userName).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (userDTO != null && passwordEncoder.matches(changePassword.oldPassword(), userDTO.getPassword())) {
            userDTO.setPassword(passwordEncoder.encode(changePassword.newPassword()));
            UserDTO dto = userRepository.save(userDTO);
            return jwtUtil.generateToken(dto.getEmail());
        }
        throw new RuntimeException("Invalid credentials");
    }
}


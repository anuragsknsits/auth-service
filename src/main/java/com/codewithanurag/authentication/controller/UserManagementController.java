package com.codewithanurag.authentication.controller;

import com.codewithanurag.authentication.entity.UserProfile;
import com.codewithanurag.authentication.model.Profile;
import com.codewithanurag.authentication.model.SignUp;
import com.codewithanurag.authentication.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class UserManagementController {

    private final UserService userService;

    public UserManagementController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Profile> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        Profile profile = userService.getUserProfile(userDetails.getUsername());
        return ResponseEntity.ok(profile);
    }

    @PutMapping
    public ResponseEntity<UserProfile> updateProfile(@RequestBody Profile updatedProfile,
                                                     @AuthenticationPrincipal UserDetails userDetails) {
        UserProfile profile = userService.updateUserProfile(userDetails.getUsername(), updatedProfile);
        return ResponseEntity.ok(profile);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody SignUp user) {
        userService.registerUser(user);
        return ResponseEntity.ok("User created successfully");
    }

    /*@PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'HR')")
    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @RequestBody SignUp user) {
        userService.updateUser(userId, user);
        return ResponseEntity.ok("User updated successfully");
    }*/

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }
}


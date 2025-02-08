package com.codewithanurag.authentication.repository;

import com.codewithanurag.authentication.entity.UserDTO;
import com.codewithanurag.authentication.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUser(UserDTO userDTO);
}
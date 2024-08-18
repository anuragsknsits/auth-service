package com.codewithanurag.authentication.repository;

import com.codewithanurag.authentication.entity.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDTO, Long> {
    Optional<UserDTO> findByEmail(String email);
}


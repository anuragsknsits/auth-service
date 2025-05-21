package com.codewithanurag.authentication.repository;

import com.codewithanurag.authentication.entity.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDTO, Long> {
    @Query("SELECT u FROM UserDTO u JOIN FETCH u.role WHERE u.email = :email")
    Optional<UserDTO> findByEmail(@Param("email") String email);
}


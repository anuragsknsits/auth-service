package com.codewithanurag.authentication.service.impl;

import com.codewithanurag.authentication.entity.UserDTO;
import com.codewithanurag.authentication.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userEntityRepository;

    public UserDetailsServiceImpl(UserRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO = userEntityRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new User(userDTO.getEmail(), userDTO.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(userDTO.getRole().getName())));
    }
}

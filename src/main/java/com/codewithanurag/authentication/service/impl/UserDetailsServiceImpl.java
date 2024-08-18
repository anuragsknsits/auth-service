package com.codewithanurag.authentication.service.impl;

import com.codewithanurag.authentication.entity.UserEntity;
import com.codewithanurag.authentication.repository.UserEntityRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserEntityRepository userEntityRepository;

    public UserDetailsServiceImpl(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userEntityRepository.findByEmailId(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new User(userEntity.getEmailId(), userEntity.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(userEntity.getRole())));
    }
}

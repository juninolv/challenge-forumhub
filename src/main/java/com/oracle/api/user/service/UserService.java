package com.oracle.api.user.service;

import com.oracle.api.security.service.JwtService;
import com.oracle.api.user.dto.UserPublicDto;
import com.oracle.api.user.dto.UserRegisterDto;
import com.oracle.api.user.dto.UserTokenDto;
import com.oracle.api.user.model.User;
import com.oracle.api.user.repository.UserRepository;
import com.oracle.api.user.util.exception.UserException;
import com.oracle.api.user.util.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public UserPublicDto register(UserRegisterDto data) {
        User entity = new User(data);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));

        return mapper.toPublic(repository.save(entity));
    }

    public UserTokenDto login(Authentication data) {
        return new UserTokenDto(jwtService.encode(data));
    }

    public UserPublicDto readById(UUID id) {
        return repository
            .findById(id)
            .map(mapper::toPublic)
            .orElseThrow(() -> new UserException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}

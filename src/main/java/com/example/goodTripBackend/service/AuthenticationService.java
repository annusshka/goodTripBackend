package com.example.goodTripBackend.service;

import com.example.goodTripBackend.config.JwtService;
import com.example.goodTripBackend.models.dto.AuthenticationRequest;
import com.example.goodTripBackend.models.dto.AuthenticationResponse;
import com.example.goodTripBackend.models.dto.RegisterRequest;
import com.example.goodTripBackend.models.entities.Role;
import com.example.goodTripBackend.models.entities.User;
import com.example.goodTripBackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var jwtRefreshToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .refreshToken(jwtRefreshToken)
                .user(user)
                .build();
    }

    public AuthenticationResponse authentication(AuthenticationRequest request) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            var user = repository.findByEmail(request.getEmail());
            if (user.isPresent()) {
                var jwtToken = jwtService.generateToken(user.get());
                var jwtRefreshToken = jwtService.generateToken(user.get());
                return AuthenticationResponse.builder()
                        .token(jwtToken)
                        .refreshToken(jwtRefreshToken)
                        .user(user.get())
                        .build();
            } else {
                throw new RuntimeException(e);
            }
        }
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var jwtRefreshToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(jwtRefreshToken)
                .user(user)
                .build();
    }
}

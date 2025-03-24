package com.example.goodTripBackend.features.auth.service;

import com.example.goodTripBackend.domain.JwtService;
import com.example.goodTripBackend.features.auth.models.dto.AuthenticationRequest;
import com.example.goodTripBackend.features.auth.models.dto.AuthenticationResponse;
import com.example.goodTripBackend.features.auth.models.dto.RegisterRequest;
import com.example.goodTripBackend.features.auth.models.entities.EmailTemplateName;
import com.example.goodTripBackend.features.auth.models.entities.Token;
import com.example.goodTripBackend.features.user.models.entities.Role;
import com.example.goodTripBackend.features.user.models.entities.User;
import com.example.goodTripBackend.features.auth.repository.TokenRepository;
import com.example.goodTripBackend.features.user.repository.UserRepository;
import com.example.goodTripBackend.features.user.service.RoleService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) throws MessagingException {
        var userRole = roleService.getRole(Role.USER.name());
        var user = User.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(true)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
        //sendValidationEmail(user);
        var jwtToken = jwtService.generateToken(user);
        var jwtRefreshToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .refreshToken(jwtRefreshToken)
                .user(user)
                .build();
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);

        emailService.sendEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
        );
    }

    private String generateAndSaveActivationToken(User user) {
        // generate a token
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(30))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(final int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomChar = random.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomChar));
        }
        return codeBuilder.toString();
    }

    public AuthenticationResponse authentication(AuthenticationRequest request) {
//        try {
//            authManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            request.getEmail(),
//                            request.getPassword()
//                    )
//            );
//        } catch (AuthenticationException e) {
//            var user = userRepository.findByEmail(request.getEmail());
//            if (user.isPresent()) {
//                var jwtToken = jwtService.generateToken(user.get());
//                var jwtRefreshToken = jwtService.generateToken(user.get());
//                return AuthenticationResponse.builder()
//                        .token(jwtToken)
//                        .refreshToken(jwtRefreshToken)
//                        .user(user.get())
//                        .build();
//            } else {
//                throw new RuntimeException(e);
//            }
//        }
//        var user = userRepository.findByEmail(request.getEmail())
//                .orElseThrow();
//        var jwtToken = jwtService.generateToken(user);
//        var jwtRefreshToken = jwtService.generateToken(user);
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .refreshToken(jwtRefreshToken)
//                .user(user)
//                .build();
        var auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("fullName", user.fullName());

        var jwtToken = jwtService.generateToken(claims, (User) auth.getPrincipal());
        var jwtRefreshToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(jwtRefreshToken)
                .user(user)
                .build();
    }
}

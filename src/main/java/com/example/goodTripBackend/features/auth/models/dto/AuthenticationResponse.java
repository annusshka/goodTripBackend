package com.example.goodTripBackend.features.auth.models.dto;

import com.example.goodTripBackend.features.user.models.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String token;

    private String refreshToken;

    private User user;
}

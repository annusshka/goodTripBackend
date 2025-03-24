package com.example.goodTripBackend.features.auth.controller;

import com.example.goodTripBackend.features.auth.models.dto.AuthenticationRequest;
import com.example.goodTripBackend.features.auth.models.dto.AuthenticationResponse;
import com.example.goodTripBackend.features.auth.models.dto.RegisterRequest;
import com.example.goodTripBackend.features.auth.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid RegisterRequest request
    ) {
        try {
            //service.register(request);
            return ResponseEntity.ok(service.register(request));
        }
        catch (DataIntegrityViolationException e) {
            return ResponseEntity.unprocessableEntity().build();
        }
        catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> authentication(
            @RequestBody @Valid AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authentication(request));
    }
}

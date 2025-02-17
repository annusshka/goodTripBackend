package com.example.goodTripBackend.features.user.service;

import com.example.goodTripBackend.features.user.models.entities.UserRole;
import com.example.goodTripBackend.features.user.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public UserRole getRole(String roleName) {
        var userRole = roleRepository.findByRole(roleName);
        if (userRole.isEmpty()) {
            var role = UserRole.builder().role(roleName).build();
            roleRepository.save(role);
            return role;
        } else {
            return userRole.get();
        }
    }
}

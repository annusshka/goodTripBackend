package com.example.goodTripBackend.features.user.repository;

import com.example.goodTripBackend.features.user.models.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<UserRole, Long> {

    Optional<UserRole> findByRole(String role);
}

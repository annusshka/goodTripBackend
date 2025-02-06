package com.example.goodTripBackend.features.auth.repository;

import com.example.goodTripBackend.features.user.models.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<UserRole, Long> {

    Optional<UserRole> findByRole(String role);
}

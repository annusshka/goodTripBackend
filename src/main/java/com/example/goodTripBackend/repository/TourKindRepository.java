package com.example.goodTripBackend.repository;

import com.example.goodTripBackend.models.entities.TourKind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TourKindRepository extends JpaRepository<TourKind, Long> {

    Optional<TourKind> findByKind(String kind);
}

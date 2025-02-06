package com.example.goodTripBackend.features.tour.repository;

import com.example.goodTripBackend.features.tour.models.entities.TourKind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TourKindRepository extends JpaRepository<TourKind, Long> {

    Optional<TourKind> findByKind(String kind);
}

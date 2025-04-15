package com.example.goodTripBackend.features.tour.repository;

import com.example.goodTripBackend.features.tour.models.entities.AudioExcursion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AudioExcursionRepository extends JpaRepository<AudioExcursion, Long> {

    List<AudioExcursion> findByAddressCity(String city);
}

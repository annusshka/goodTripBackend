package com.example.goodTripBackend.features.tour.repository;

import com.example.goodTripBackend.features.tour.models.entities.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {

    // List<Tour> findByCity(String city);
}

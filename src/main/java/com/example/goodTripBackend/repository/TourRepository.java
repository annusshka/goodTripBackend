package com.example.goodTripBackend.repository;

import com.example.goodTripBackend.models.entities.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {

    // List<Tour> findByCity(String city);
}

package com.example.goodTripBackend.features.tour.repository;

import com.example.goodTripBackend.features.tour.models.entities.AudioFile;
import com.example.goodTripBackend.features.tour.models.entities.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AudioFileRepository extends JpaRepository<AudioFile, Long> {
    Optional<AudioFile> findByTour(Tour tour);
}

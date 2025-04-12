package com.example.goodTripBackend.features.tour.repository;

import com.example.goodTripBackend.features.tour.models.entities.Address;
import com.example.goodTripBackend.features.tour.models.entities.AudioExcursion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AudioExcursionRepository extends JpaRepository<AudioExcursion, Long> {

    Optional<AudioExcursion> findByAddress(Address address);

    List<AudioExcursion> findByAddressCity(String city);

    @Query("SELECT a FROM AudioExcursion a WHERE a.address.city = :city")
    List<AudioExcursion> findByAddressCity(@Param("city") String city, Pageable pageable);
}

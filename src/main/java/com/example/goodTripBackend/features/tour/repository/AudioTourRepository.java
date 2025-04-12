package com.example.goodTripBackend.features.tour.repository;

import com.example.goodTripBackend.features.tour.models.entities.Address;
import com.example.goodTripBackend.features.tour.models.entities.Tour;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AudioTourRepository extends JpaRepository<Tour, Long> {

    Optional<Tour> findByAddress(Address address);

    @Query("SELECT a FROM Tour a JOIN a.address addr WHERE addr.city = :city")
    List<Tour> findByAddressCity(@Param("city") String city, Pageable pageable);
}

package com.example.goodTripBackend.repository;

import com.example.goodTripBackend.models.entities.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Page<Address> findAllByCity(String city, Pageable pageable);
}

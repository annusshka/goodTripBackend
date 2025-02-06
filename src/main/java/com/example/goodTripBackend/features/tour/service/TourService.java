package com.example.goodTripBackend.features.tour.service;

import com.example.goodTripBackend.features.tour.models.entities.Address;
import com.example.goodTripBackend.features.tour.models.entities.Tour;
import com.example.goodTripBackend.features.tour.repository.AddressRepository;
import com.example.goodTripBackend.features.tour.repository.TourRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.min;

@Service
@AllArgsConstructor
public class TourService {

    private final TourRepository repository;

    private final AddressRepository addressRepository;

    public Long save(Tour tour) {
        return repository.save(tour).getId();
    }

    public Tour findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

//    public List<Tour> findToursById(Long beginId, Long count) {
//        List<Tour> tours = new ArrayList<>();
//        count = min(beginId + count, repository.count());
//
//        while (beginId <= count) {
//            Optional<Tour> tour = repository.findById(beginId);
//            tour.ifPresent(tours::add);
//            beginId++;
//        }
//        return tours;
//    }

//    public List<Tour> findToursByPage(int page) {
//        return findToursById(page * 100L, 100L);
//    }

//    public List<Tour> findToursByCity(String city) {
//        List<Long> tourIds = addressRepository.findByCity(city);
//        List<Tour> tours = new ArrayList<>();
//        for (Long tourId : tourIds) {
//            Optional<Tour> tour = repository.findById(tourId);
//            tour.ifPresent(tours::add);
//        }
//        return tours;
//    }

    public List<Tour> findAllByCity(String city, int offset, int limit) {
        Page<Address> addresses = addressRepository.findAllByCity(city, PageRequest.of(offset, limit));
        List<Tour> tours = new ArrayList<>();
        for (Address address : addresses.getContent()) {
            Long id = address.getId();
            Optional<Tour> tour = repository.findById(id);
            tour.ifPresent(tours::add);
        }
        return tours;
    }

    public List<Tour> findAll(int offset, int limit) {
        return repository.findAll(PageRequest.of(offset, limit)).getContent();
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }
}

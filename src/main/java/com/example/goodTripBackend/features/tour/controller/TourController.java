package com.example.goodTripBackend.features.tour.controller;

import com.example.goodTripBackend.features.tour.models.entities.Tour;
import com.example.goodTripBackend.features.tour.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("auth/tours")
@RequiredArgsConstructor
public class TourController {

    private final TourService service;

//    @PostMapping
//    public ResponseEntity<Long> save(
//            @RequestBody Tour tour
//    ) {
//        return ResponseEntity.ok(service.save(tour));
//    }

//    @GetMapping
//    public ResponseEntity<List<Tour>> findAll() {
//        return ResponseEntity.ok(service.findAll());
//    }

    @GetMapping("/{tour_id}")
    public ResponseEntity<Tour> findById(
            @PathVariable("tour_id") Long id
    ) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/city/{city_name}")
    public ResponseEntity<List<Tour>> findAllByCity(
            @PathVariable("city_name") String city,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit
    ) {
        return ResponseEntity.ok(service.findAllByCity(city, offset, limit));
    }

    @GetMapping
    public ResponseEntity<List<Tour>> findAll(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit
    ) {
        return ResponseEntity.ok(service.findAll(offset, limit));
    }

//    @GetMapping("/pages/{page}")
//    public ResponseEntity<List<Tour>> findAudioToursByPage(
//            @PathVariable("page") int page
//    ) {
//        return ResponseEntity.ok(service.findToursByPage(page));
//    }

//    @GetMapping("/pages/{begin_id}")
//    public ResponseEntity<List<Tour>> findHundredAudio(
//            @PathVariable("begin_id") Long beginId
//    ) {
//        return ResponseEntity.ok(service.findHundredToursById(beginId));
//    }

//    @GetMapping("/city/{city}/{page}")
//    public ResponseEntity<List<Tour>> findByCity(
//            @PathVariable("city") String city,
//            @PathVariable("page") int page
//    ) {
//        return ResponseEntity.ok(service.findToursByCity(city));
//    }

//    @DeleteMapping("/{tour_id}")
//    public ResponseEntity<Void> delete(
//            @PathVariable("tour_id") Long id
//    ) {
//        service.delete(id);
//        return ResponseEntity.accepted().build();
//    }
}


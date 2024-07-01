package com.example.goodTripBackend.controller;

import com.example.goodTripBackend.models.dto.AudioTourDto;
import com.example.goodTripBackend.models.dto.TourDto;
import com.example.goodTripBackend.models.entities.Tour;
import com.example.goodTripBackend.models.entities.User;
import com.example.goodTripBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    /*@PostMapping
    public ResponseEntity<Long> save(
            @RequestBody User user
    ) {
        return ResponseEntity.ok(service.save(user));
    }*/

//    @GetMapping("/{user_id}")
//    public ResponseEntity<User> findById(
//            @PathVariable("user_id") Long id
//    ) {
//        return ResponseEntity.ok(service.findById(id));
//    }

//    @GetMapping("/emails/{email}")
//    public ResponseEntity<User> findByEmail(
//            @RequestBody String password,
//            @PathVariable("email") String email
//    ) {
//        return ResponseEntity.ok(service.findByEmail(email));
//    }

    @GetMapping("/{user_id}")
    public ResponseEntity<User> findUserById(
            @PathVariable("user_id") Long id
    ) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/likes/{user_id}")
    public ResponseEntity<List<TourDto>> findLikesByUserId(
            @PathVariable("user_id") Long id
    ) {
        return ResponseEntity.ok(service.findLikesByUserId(id));
    }

    @PutMapping("/likes")
    public ResponseEntity<Void> addLike(
            @RequestParam("user_id") Long userId,
            @RequestParam("tour_id") Long tourId
    ) {
        service.addLike(userId, tourId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/created/{user_id}")
    public ResponseEntity<List<AudioTourDto>> findCreatedByUserId(
            @PathVariable("user_id") Long id
    ) {
        return ResponseEntity.ok(service.findCreatedToursByUserId(id));
    }
}

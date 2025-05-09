package com.example.goodTripBackend.features.user.controller;

import com.example.goodTripBackend.features.user.models.entities.User;
import com.example.goodTripBackend.features.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
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

    @GetMapping("")
    public ResponseEntity<User> findUserById(
            @AuthenticationPrincipal User userDetails
    ) {
        try {
            Long userId = userDetails.getId();
            User user = service.findById(userId);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
//
//    @GetMapping("/likes/excursions/{user_id}")
//    public ResponseEntity<List<AudioExcursionDto>> findAudioExcursionsLikes(
//            @PathVariable("user_id") Long id
//    ) {
//        try {
//            List<AudioExcursionDto> audioExcursionDtos = service.findAudioExcursionLikesByUserId(id);
//            return ResponseEntity.ok().body(audioExcursionDtos);
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
//    }

//    @GetMapping("/likes/tours/{user_id}")
//    public ResponseEntity<List<AudioTourDto>> findTourLikes(
//            @PathVariable("user_id") Long id
//    ) {
//        try {
//            List<AudioTourDto> audioTourDtos = service.findTourLikesByUserId(id);
//            return ResponseEntity.ok().body(audioTourDtos);
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PutMapping("/likes/tour")
//    public ResponseEntity<Void> likeAudioTour(
//            @RequestParam("user_id") Long userId,
//            @RequestParam("tour_id") Long tourId,
//            @RequestParam("is_liked") boolean isLiked
//    ) {
//        try {
//            service.addTourLike(userId, tourId, isLiked);
//            return ResponseEntity.accepted().build();
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
//    }

//    @PutMapping("/likes/excursion")
//    public ResponseEntity<Void> likeExcursion(
//            @RequestParam("user_id") Long userId,
//            @RequestParam("excursion_id") Long tourId,
//            @RequestParam("is_liked") boolean isLiked
//    ) {
//        try {
//            service.addAudioExcursionLike(userId, tourId, isLiked);
//            return ResponseEntity.accepted().build();
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
//    }

//    @GetMapping("/created/tour/{user_id}")
//    public ResponseEntity<List<AudioTourDto>> findCreatedAudioTours(
//            @PathVariable("user_id") Long id
//    ) {
//        try {
//            List<AudioTourDto> createdAudioTourDtos = service.findCreatedTours(id);
//            return ResponseEntity.ok().body(createdAudioTourDtos);
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
//    }

//    @GetMapping("/created/excursion/{user_id}")
//    public ResponseEntity<List<AudioExcursionDto>> findCreatedAudioExcursions(
//            @PathVariable("user_id") Long id
//    ) {
//        try {
//            List<AudioExcursionDto> createdAudioExcursionDtos = service.findCreatedAudioExcursions(id);
//            return ResponseEntity.ok().body(createdAudioExcursionDtos);
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
//    }

//    @GetMapping("excursion/city")
//    public ResponseEntity<List<AudioExcursionDto>> findAllByCity(
//            @RequestParam("city") String city,
//            @RequestParam(value = "offset", defaultValue = "0") int offset,
//            @RequestParam(value = "limit", defaultValue = "100") int limit
//    ) {
//        try {
//            List<AudioExcursionDto> audioExcursionDtoList = service.getAudioExcursionsByCity(city);
////            List<AudioExcursionDto> audioExcursionDtoList = audioExcursionService.findAllByCity(city, userId, offset, limit);
//            return ResponseEntity.ok().body(audioExcursionDtoList);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
}

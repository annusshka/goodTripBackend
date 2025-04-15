package com.example.goodTripBackend.features.tour.controller;

import com.example.goodTripBackend.features.tour.models.dto.AudioTourDto;
import com.example.goodTripBackend.features.tour.service.AudioTourService;
import com.example.goodTripBackend.features.user.models.entities.User;
import com.example.goodTripBackend.features.user.models.entities.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("audiotours")
@RequiredArgsConstructor
public class AudioTourController {

    private final AudioTourService audioTourService;

    @PostMapping("/create")
    public @ResponseBody ResponseEntity<Long> save(
            @AuthenticationPrincipal User userDetails,
            @RequestBody AudioTourDto tour
    ) {
        try {
            Long userId = userDetails.getId();
            return ResponseEntity.ok(audioTourService.save(tour, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/create/files")
    public @ResponseBody ResponseEntity<Long> save(
            @AuthenticationPrincipal User userDetails,
            @RequestParam("tour_id") Long tourId,
            @RequestParam("image") MultipartFile image
    ) {
        try {
            Long userId = userDetails.getId();
            return ResponseEntity.ok(audioTourService.saveFiles(tourId, image, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

//    @PostMapping("/created/{user_id}")
//    public @ResponseBody ResponseEntity<Long> saveTour(
//            @PathVariable("user_id") Long userId,
//            @RequestBody AudioTourDto tour
//    ) {
//        return ResponseEntity.ok(audioTourService.saveTour(tour, userId));
//    }
//
//    @PostMapping(value = "/created/files/{tour_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public @ResponseBody ResponseEntity<Long> saveFiles(
//            @PathVariable("tour_id") Long tourId,
//            @RequestParam("files") Map<String, MultipartFile> files
//    ) {
//        try {
//            return ResponseEntity.ok(audioTourService.saveFiles(tourId, files.get("image"), files.get("audio")));
//        } catch (IOException e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }

//    @PostMapping("/created/files/{tour_id}")
//    public @ResponseBody ResponseEntity<Long> saveFiles(
//            @PathVariable("tour_id") Long tourId,
//            @RequestParam("image") MultipartFile image,
//            @RequestParam("audio") MultipartFile audio
//    ) {
//        try {
//            return ResponseEntity.ok(audioTourService.saveFiles(tourId, image, audio));
//        } catch (IOException e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }

//    @PostMapping("/created/files/image/{tour_id}")
//    public @ResponseBody ResponseEntity<Long> saveImageFile(
//            @PathVariable("tour_id") Long tourId,
//            @RequestParam("multipartFiles[]") List<MultipartFile> image
//    ) {
//        try {
//            return ResponseEntity.ok(audioTourService.saveImageFile(tourId, image.get(0)));
//        } catch (IOException e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
//
//    @PostMapping("/created/files/audio/{tour_id}")
//    public @ResponseBody ResponseEntity<Long> saveAudioFile(
//            @PathVariable("tour_id") Long tourId,
//            @RequestParam("audio") MultipartFile audio
//    ) {
//        try {
//            return ResponseEntity.ok(audioTourService.saveAudioFile(tourId, audio));
//        } catch (IOException e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }

//    @GetMapping
//    public ResponseEntity<AudioTourDto> findById(
//            @RequestParam("user_id") Long userId,
//            @RequestParam("tourId") Long id
//    ) {
//        return ResponseEntity.ok(audioTourService.findById(userId, id));
//    }
//
//    @GetMapping("/{user_id}")
//    public ResponseEntity<List<AudioTourDto>> findAllByUser(
//            @PathVariable("user_id") Long userId,
//            @RequestParam(value = "offset", defaultValue = "0") int offset,
//            @RequestParam(value = "limit", defaultValue = "100") int limit
//    ) {
//        return ResponseEntity.ok(audioTourService.findAllByUser(userId, offset, limit));
//    }

    @GetMapping("/city")
    public ResponseEntity<List<AudioTourDto>> findAllByCity(
            @AuthenticationPrincipal User userDetails,
            @RequestParam("city") String city,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit
    ) {
        Long userId = userDetails.getId();
        return ResponseEntity.ok(audioTourService.getAudioToursByCity(city, userId));
    }

    @GetMapping("/like")
    public ResponseEntity<List<AudioTourDto>> findTourLikes(
            @AuthenticationPrincipal User userDetails
    ) {
        try {
            Long userId = userDetails.getId();
            List<AudioTourDto> audioTourDtos = audioTourService.findTourLikesByUserId(userId);
            return ResponseEntity.ok().body(audioTourDtos);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/like")
    public ResponseEntity<Void> likeAudioTour(
            @AuthenticationPrincipal User userDetails,
            @RequestParam("tour_id") Long tourId,
            @RequestParam("is_liked") boolean isLiked
    ) {
        try {
            Long userId = userDetails.getId();
            audioTourService.addTourLike(userId, tourId, isLiked);
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/created")
    public ResponseEntity<List<AudioTourDto>> findCreatedAudioTours(
            @AuthenticationPrincipal User userDetails
    ) {
        try {
            Long userId = userDetails.getId();
            List<AudioTourDto> createdAudioTourDtos = audioTourService.findCreatedTours(userId);
            return ResponseEntity.ok().body(createdAudioTourDtos);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(
            @AuthenticationPrincipal User userDetails,
            @RequestParam("tour_id") Long tourId
    ) {
        try {
            Long userId = userDetails.getId();
            audioTourService.deleteById(tourId, userId);
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

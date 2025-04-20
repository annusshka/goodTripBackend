package com.example.goodTripBackend.features.tour.controller;

import com.example.goodTripBackend.features.tour.models.dto.AudioExcursionDto;
import com.example.goodTripBackend.features.tour.service.AudioExcursionService;
import com.example.goodTripBackend.features.user.models.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("audioexcursion")
@RequiredArgsConstructor
public class AudioExcursionController {

    private final AudioExcursionService audioExcursionService;

    @GetMapping("/like")
    public ResponseEntity<List<AudioExcursionDto>> findAudioExcursionsLikes(
            @AuthenticationPrincipal User userDetails
    ) {
        try {
            Long userId = userDetails.getId();
            List<AudioExcursionDto> audioExcursionDtos = audioExcursionService.findAudioExcursionLikesByUserId(userId);
            return ResponseEntity.ok().body(audioExcursionDtos);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/like")
    public ResponseEntity<Void> likeExcursion(
            @RequestParam("excursion_id") Long tourId,
            @RequestParam("is_liked") boolean isLiked,
            @AuthenticationPrincipal User userDetails
    ) {
        try {
            Long userId = userDetails.getId();
            audioExcursionService.addAudioExcursionLike(userId, tourId, isLiked);
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/created")
    public ResponseEntity<List<AudioExcursionDto>> findCreatedAudioExcursions(
            @AuthenticationPrincipal User userDetails
    ) {
        try {
            Long userId = userDetails.getId();
            List<AudioExcursionDto> createdAudioExcursionDtos = audioExcursionService.findCreatedAudioExcursions(userId);
            return ResponseEntity.ok().body(createdAudioExcursionDtos);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("city")
    public ResponseEntity<List<AudioExcursionDto>> findAllByCity(
            @AuthenticationPrincipal User userDetails,
            @RequestParam("city") String city,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit
    ) {
        try {
            Long userId = userDetails.getId();
            List<AudioExcursionDto> audioExcursionDtoList = audioExcursionService.getAudioExcursionsByCity(city, userId);
            return ResponseEntity.ok().body(audioExcursionDtoList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/create")
    public @ResponseBody ResponseEntity<Long> save(
            @AuthenticationPrincipal User userDetails,
            @RequestBody AudioExcursionDto excursionDto
    ) {
        try {
            Long userId = userDetails.getId();
            return ResponseEntity.ok(audioExcursionService.save(excursionDto, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/create/image")
    public @ResponseBody ResponseEntity<Long> saveImage(
            @AuthenticationPrincipal User userDetails,
            @RequestParam("excursion_id") Long excursionId,
            @RequestParam("image") MultipartFile image
    ) {
        try {
            Long userId = userDetails.getId();
            return ResponseEntity.ok(audioExcursionService.saveImageFile(excursionId, image, userId));
        } catch (Exception e) {
            try {
                Long userId = userDetails.getId();
                audioExcursionService.deleteById(userId, excursionId);
                return ResponseEntity.accepted().build();
            } catch (Exception ex) {
                return ResponseEntity.badRequest().build();
            }
        }
    }

    @PostMapping("/create/audio")
    public @ResponseBody ResponseEntity<Long> saveAudio(
            @AuthenticationPrincipal User userDetails,
            @RequestParam("excursion_id") Long excursionId,
            @RequestParam("audio") MultipartFile audio
    ) {
        try {
            Long userId = userDetails.getId();
            return ResponseEntity.ok(audioExcursionService.saveAudioFile(excursionId, audio, userId));
        } catch (Exception e) {
            try {
                Long userId = userDetails.getId();
                audioExcursionService.deleteById(userId, excursionId);
                return ResponseEntity.accepted().build();
            } catch (Exception ex) {
                return ResponseEntity.badRequest().build();
            }
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(
            @AuthenticationPrincipal User userDetails,
            @RequestParam("excursion_id") Long excursionId
    ) {
        try {
            Long userId = userDetails.getId();
            audioExcursionService.deleteById(userId, excursionId);
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

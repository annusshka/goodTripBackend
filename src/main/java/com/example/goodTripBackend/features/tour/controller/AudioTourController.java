package com.example.goodTripBackend.features.tour.controller;

import com.example.goodTripBackend.features.tour.models.dto.AudioTourDto;
import com.example.goodTripBackend.features.tour.service.AudioTourService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("auth/tours/audio")
@RequiredArgsConstructor
public class AudioTourController {

    private final AudioTourService audioTourService;

    @PostMapping("/created/full/{user_id}")
    public @ResponseBody ResponseEntity<Long> save(
            @PathVariable("user_id") Long userId,
            @RequestBody AudioTourDto tour,
            @RequestParam("image") MultipartFile image,
            @RequestParam("audio") MultipartFile audio
    ) {
        try {
            return ResponseEntity.ok(audioTourService.save(tour, image, audio, userId));
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/created/{user_id}")
    public @ResponseBody ResponseEntity<Long> saveTour(
            @PathVariable("user_id") Long userId,
            @RequestBody AudioTourDto tour
    ) {
        return ResponseEntity.ok(audioTourService.saveTour(tour, userId));
    }

    @PostMapping(value = "/created/files/{tour_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody ResponseEntity<Long> saveFiles(
            @PathVariable("tour_id") Long tourId,
            @RequestParam("files") Map<String, MultipartFile> files
    ) {
        try {
            return ResponseEntity.ok(audioTourService.saveFiles(tourId, files.get("image"), files.get("audio")));
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

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

    @PostMapping("/created/files/image/{tour_id}")
    public @ResponseBody ResponseEntity<Long> saveImageFile(
            @PathVariable("tour_id") Long tourId,
            @RequestParam("multipartFiles[]") List<MultipartFile> image
    ) {
        try {
            return ResponseEntity.ok(audioTourService.saveImageFile(tourId, image.get(0)));
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/created/files/audio/{tour_id}")
    public @ResponseBody ResponseEntity<Long> saveAudioFile(
            @PathVariable("tour_id") Long tourId,
            @RequestParam("audio") MultipartFile audio
    ) {
        try {
            return ResponseEntity.ok(audioTourService.saveAudioFile(tourId, audio));
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<AudioTourDto> findById(
            @RequestParam("user_id") Long userId,
            @RequestParam("tourId") Long id
    ) {
        return ResponseEntity.ok(audioTourService.findById(userId, id));
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<List<AudioTourDto>> findAllByUser(
            @PathVariable("user_id") Long userId,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit
    ) {
        return ResponseEntity.ok(audioTourService.findAllByUser(userId, offset, limit));
    }

    @GetMapping("/city/{user_id}")
    public ResponseEntity<List<AudioTourDto>> findAllByCity(
            @PathVariable("user_id") Long userId,
            @RequestParam("city") String city,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit
    ) {
        return ResponseEntity.ok(audioTourService.findAllByCity(city, userId, offset, limit));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(
            @RequestParam("user_id") Long userId,
            @RequestParam("tourId") Long tourId
    ) {
        audioTourService.deleteById(userId, tourId);
        return ResponseEntity.accepted().build();
    }
}

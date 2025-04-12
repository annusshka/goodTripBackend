//package com.example.goodTripBackend.features.tour.controller;
//
//import com.example.goodTripBackend.features.tour.models.dto.AudioExcursionDto;
//import com.example.goodTripBackend.features.tour.service.AudioExcursionService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.List;
//
//@RestController
//@RequestMapping("auth/excursions/audio")
//@RequiredArgsConstructor
//public class AudioExcursionController {
//
//    private final AudioExcursionService audioExcursionService;
//
//    @PostMapping("/created/full/{user_id}")
//    public @ResponseBody ResponseEntity<Long> save(
//            @PathVariable("user_id") Long userId,
//            @RequestBody AudioExcursionDto audioExcursion,
//            @RequestParam("image") MultipartFile image,
//            @RequestParam("audio") MultipartFile audio
//    ) {
//        try {
//            Long savedId = audioExcursionService.save(audioExcursion, image, audio, userId);
//            return ResponseEntity.ok().body(savedId);
//        } catch (IOException e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
//
////    @GetMapping
////    public ResponseEntity<AudioExcursionDto> findById(
////            @RequestParam("user_id") Long userId,
////            @RequestParam("tourId") Long id
////    ) {
////        try {
////            return ResponseEntity.ok().body(audioExcursionService.findById(userId, id));
////        } catch (Exception e) {
////            return ResponseEntity.badRequest().build();
////        }
////    }
//
//    @GetMapping("/{user_id}")
//    public ResponseEntity<List<AudioExcursionDto>> findAllByUser(
//            @PathVariable("user_id") Long userId,
//            @RequestParam(value = "offset", defaultValue = "0") int offset,
//            @RequestParam(value = "limit", defaultValue = "100") int limit
//    ) {
//        return ResponseEntity.ok().body(audioExcursionService.findAllByUser(userId, offset, limit));
//    }
//
//    @GetMapping("/city/{user_id}")
//    public ResponseEntity<List<AudioExcursionDto>> findAllByCity(
//            @PathVariable("user_id") Long userId,
//            @RequestParam("city") String city,
//            @RequestParam(value = "offset", defaultValue = "0") int offset,
//            @RequestParam(value = "limit", defaultValue = "100") int limit
//    ) {
//        try {
//            List<AudioExcursionDto> audioExcursionDtoList = audioExcursionService.findAllByCity(city, userId, offset, limit);
//            return ResponseEntity.ok().body(audioExcursionDtoList);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
//
//    @DeleteMapping
//    public ResponseEntity<Void> deleteById(
//            @RequestParam("user_id") Long userId,
//            @RequestParam("tourId") Long tourId
//    ) {
//        audioExcursionService.deleteById(userId, tourId);
//        return ResponseEntity.accepted().build();
//    }
//}

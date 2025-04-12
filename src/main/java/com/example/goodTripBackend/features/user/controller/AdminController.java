//package com.example.goodTripBackend.features.user.controller;
//
//import com.example.goodTripBackend.features.tour.models.dto.AudioExcursionDto;
//import com.example.goodTripBackend.features.tour.service.AudioExcursionService;
//import com.example.goodTripBackend.features.user.models.dto.AccountDto;
//import com.example.goodTripBackend.features.tour.models.dto.AudioTourDto;
//import com.example.goodTripBackend.features.tour.service.AudioTourService;
//import com.example.goodTripBackend.features.user.service.UserService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("auth/admin")
//@RequiredArgsConstructor
//@SecurityRequirement(name = "bearerAuth")
//@Tag(name = "Admin")
//public class AdminController {
//
//    private final UserService userService;
//
//    private final AudioTourService audioTourService;
//
//    private final AudioExcursionService audioExcursionService;
//
//    @GetMapping("/users")
//    @Operation(
//            description = "Get all users",
//            summary = "This is summary for get all users request",
//            responses = {
//                    @ApiResponse(
//                            description = "Success",
//                            responseCode = "200"
//                    ),
//                    @ApiResponse(
//                            description = "Unauthorized / Invalid Token",
//                            responseCode = "403"
//                    )
//            }
//    )
//    public ResponseEntity<List<AccountDto>> findAllUsers(
//            @RequestParam(value = "offset", defaultValue = "0") int offset,
//            @RequestParam(value = "limit", defaultValue = "100") int limit
//    ) {
//        return ResponseEntity.ok(userService.findAll(offset, limit));
//    }
//
//    @DeleteMapping("/user/{user_id}")
//    public ResponseEntity<Void> deleteUser(
//            @PathVariable("user_id") Long id
//    ) {
//        userService.delete(id);
//        return ResponseEntity.accepted().build();
//    }
//
//    @GetMapping("/tours")
//    public ResponseEntity<List<AudioTourDto>> findAllCreatedTours(
//            @RequestParam(value = "offset", defaultValue = "0") int offset,
//            @RequestParam(value = "limit", defaultValue = "100") int limit
//    ) {
//        return ResponseEntity.ok(audioTourService.findAll(offset, limit));
//    }
//
//    @GetMapping("/excursions")
//    public ResponseEntity<List<AudioExcursionDto>> findAllCreatedExcursions(
//            @RequestParam(value = "offset", defaultValue = "0") int offset,
//            @RequestParam(value = "limit", defaultValue = "100") int limit
//    ) {
//        return ResponseEntity.ok(audioExcursionService.findAll(offset, limit));
//    }
//
//    @DeleteMapping("/tour")
//    public ResponseEntity<Void> deleteAudioTourById(
//            @RequestParam("user_id") Long userId,
//            @RequestParam("tourId") Long tourId
//    ) {
//        audioTourService.deleteById(userId, tourId);
//        return ResponseEntity.accepted().build();
//    }
//
//    @DeleteMapping("/excursion")
//    public ResponseEntity<Void> deleteAudioExcursionById(
//            @RequestParam("user_id") Long userId,
//            @RequestParam("excursion_id") Long excursionId
//    ) {
//        audioExcursionService.deleteById(userId, excursionId);
//        return ResponseEntity.accepted().build();
//    }
//}

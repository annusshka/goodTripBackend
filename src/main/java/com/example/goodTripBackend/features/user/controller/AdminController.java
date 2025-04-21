package com.example.goodTripBackend.features.user.controller;

import com.example.goodTripBackend.features.tour.models.dto.AudioExcursionDto;
import com.example.goodTripBackend.features.tour.service.AudioExcursionService;
import com.example.goodTripBackend.features.tour.service.AudioTourService;
import com.example.goodTripBackend.features.user.models.dto.AccountDto;
import com.example.goodTripBackend.features.tour.models.dto.AudioTourDto;
import com.example.goodTripBackend.features.user.models.entities.User;
import com.example.goodTripBackend.features.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
@Tag(name = "Admin")
public class AdminController {

    private final UserService userService;

    private final AudioExcursionService audioExcursionService;

    private final AudioTourService audioTourService;

    @GetMapping("/users")
    @Operation(
            description = "Get all users",
            summary = "This is summary for get all users request",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    public ResponseEntity<List<AccountDto>> findAllUsers(
            @AuthenticationPrincipal User userDetails,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit
    ) {
        Long userId = userDetails.getId();
        return ResponseEntity.ok(userService.findAll(offset, limit));
    }

    @DeleteMapping("/user")
    public ResponseEntity<Void> deleteUser(
            @AuthenticationPrincipal User userDetails
    ) {
        Long userId = userDetails.getId();
        userService.delete(userId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/tours")
    public ResponseEntity<List<AudioTourDto>> findAllCreatedTours(
            @AuthenticationPrincipal User userDetails,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit
    ) {
        Long userId = userDetails.getId();
        return ResponseEntity.ok(audioTourService.findAll(offset, limit));
    }

    @GetMapping("/excursions")
    public ResponseEntity<List<AudioExcursionDto>> findAllCreatedExcursions(
            @AuthenticationPrincipal User userDetails,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit
    ) {
        Long userId = userDetails.getId();
        return ResponseEntity.ok(audioExcursionService.findAll(offset, limit));
    }

    @DeleteMapping("/tour")
    public ResponseEntity<Void> deleteAudioTourById(
            @AuthenticationPrincipal User userDetails,
            @RequestParam("tourId") Long tourId
    ) {
        audioTourService.deleteByAdmin(tourId);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/excursion")
    public ResponseEntity<Void> deleteAudioExcursionById(
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

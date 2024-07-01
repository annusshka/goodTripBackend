package com.example.goodTripBackend.controller;

import com.example.goodTripBackend.models.dto.AccountDto;
import com.example.goodTripBackend.models.dto.AudioTourDto;
import com.example.goodTripBackend.models.entities.User;
import com.example.goodTripBackend.service.AudioTourService;
import com.example.goodTripBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    private final AudioTourService audioTourService;

    @GetMapping("/users")
    public ResponseEntity<List<AccountDto>> findAllUsers(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit
    ) {
        return ResponseEntity.ok(userService.findAll(offset, limit));
    }

    @DeleteMapping("/user/{user_id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable("user_id") Long id
    ) {
        userService.delete(id);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/tours")
    public ResponseEntity<List<AudioTourDto>> findAllCreatedTours(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit
    ) {
        return ResponseEntity.ok(audioTourService.findAll(offset, limit));
    }

    @DeleteMapping("/tour")
    public ResponseEntity<Void> deleteById(
            @RequestParam("user_id") Long userId,
            @RequestParam("tourId") Long tourId
    ) {
        audioTourService.deleteById(userId, tourId);
        return ResponseEntity.accepted().build();
    }
}

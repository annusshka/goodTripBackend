package com.example.goodTripBackend.features.user.service;

import com.example.goodTripBackend.features.tour.models.entities.AudioExcursion;
import com.example.goodTripBackend.features.tour.models.entities.Tour;
import com.example.goodTripBackend.features.tour.models.mapper.MapperAudioExcursion;
import com.example.goodTripBackend.features.tour.models.mapper.MapperAudioTour;
import com.example.goodTripBackend.features.tour.repository.AudioExcursionRepository;
import com.example.goodTripBackend.features.tour.repository.AudioTourRepository;
import com.example.goodTripBackend.features.user.models.dto.AccountDto;
import com.example.goodTripBackend.features.user.models.entities.Role;
import com.example.goodTripBackend.features.user.models.entities.User;
import com.example.goodTripBackend.features.user.models.mapper.MapperUser;
import com.example.goodTripBackend.features.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    private final AudioTourRepository audioTourRepository;

    private final AudioExcursionRepository audioExcursionRepository;

    private final MapperAudioTour mapperAudioTour;

    private final MapperUser mapperUser;

    private final MapperAudioExcursion mapperAudioExcursion;

    public List<AccountDto> findAll(int offset, int limit) {
        List<User> users = userRepository.findAll(PageRequest.of(offset, limit)).getContent();
        List<AccountDto> accounts = new ArrayList<>();
        for (User user : users) {
            // todo check roles
            boolean isUserRole = user.getRoles().contains(Role.USER);
            if (isUserRole) {
                accounts.add(mapperUser.mapToAccountDto(user));
            }
        }
        return accounts;
    }

    public void addExcursion(Long userId, Long newExcursionId) {
        User user = userRepository.findById(userId).orElseThrow();
        AudioExcursion audioExcursion = audioExcursionRepository.findById(newExcursionId).orElseThrow();
        user.getCreatedAudioExcursions().add(audioExcursion);
        userRepository.save(user);
    }

    public void addTour(Long userId, Long newTourId) {
        User user = userRepository.findById(userId).orElseThrow();
        Tour tour = audioTourRepository.findById(newTourId).orElseThrow();
        user.getCreatedTours().add(tour);
        userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}

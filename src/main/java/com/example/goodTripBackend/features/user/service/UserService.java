package com.example.goodTripBackend.features.user.service;

import com.example.goodTripBackend.features.tour.models.dto.AudioExcursionDto;
import com.example.goodTripBackend.features.tour.models.dto.AudioTourDto;
import com.example.goodTripBackend.features.tour.models.entities.Address;
import com.example.goodTripBackend.features.tour.models.entities.AudioExcursion;
import com.example.goodTripBackend.features.tour.models.entities.Tour;
import com.example.goodTripBackend.features.tour.models.mapper.MapperAudioExcursion;
import com.example.goodTripBackend.features.tour.models.mapper.MapperAudioTour;
import com.example.goodTripBackend.features.tour.repository.AddressRepository;
import com.example.goodTripBackend.features.tour.repository.AudioExcursionRepository;
import com.example.goodTripBackend.features.tour.repository.AudioTourRepository;
import com.example.goodTripBackend.features.user.models.dto.AccountDto;
import com.example.goodTripBackend.features.user.models.entities.Role;
import com.example.goodTripBackend.features.user.models.entities.User;
import com.example.goodTripBackend.features.user.models.mapper.MapperUser;
import com.example.goodTripBackend.features.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final AudioTourRepository audioTourRepository;

    private final AudioExcursionRepository audioExcursionRepository;

    private final AddressRepository addressRepository;

    private final MapperAudioTour mapperAudioTour;

    private final MapperUser mapperUser;

    private final MapperAudioExcursion mapperAudioExcursion;

    public User findById(Long id) throws Exception {
        return userRepository.findById(id).orElseThrow();
    }

    public void addTourLike(Long userId, Long likedTourId, boolean isLiked) throws Exception {
        User user = userRepository.findById(userId).orElseThrow();
        Tour tour = audioTourRepository.findById(likedTourId).orElseThrow();
        List<Tour> likes = user.getLikedTours();
        if (isLiked) {
            if (!likes.contains(tour)) {
                likes.add(tour);
            }
        } else {
            likes.remove(tour);
        }
        user.setLikedTours(likes);
        userRepository.save(user);
    }

    public List<AudioTourDto> findTourLikesByUserId(Long id) throws Exception {
        User user = userRepository.findById(id).orElseThrow();
        List<AudioTourDto> tourDtoList = new ArrayList<>();
        List<Tour> tours = user.getLikedTours();
        for (Tour tour : tours) {
            tourDtoList.add(mapperAudioTour.mapToAudioTourDto(tour, true));
        }
        return tourDtoList;
    }

    public List<AudioTourDto> findCreatedTours(Long userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow();
        List<AudioTourDto> audioTours = new ArrayList<>();
        List<Tour> createdTourList = user.getCreatedTours();
        for (Tour tour : createdTourList) {
            boolean isLiked = createdTourList.contains(tour);
            audioTours.add(mapperAudioTour.mapToAudioTourDto(tour, isLiked));
        }
        return audioTours;
    }

    public List<AccountDto> findAll(int offset, int limit) {
        List<User> users = userRepository.findAll(PageRequest.of(offset, limit)).getContent();
        List<AccountDto> accounts = new ArrayList<>();
        for (User user : users) {
            boolean isUserRole = user.getRoles().contains(Role.USER);
            if (isUserRole) {
                accounts.add(mapperUser.mapToAccountDto(user));
            }
        }
        return accounts;
    }

    public void addExcursion(Long userId, AudioExcursion audioExcursion) throws Exception {
        User user = userRepository.findById(userId).orElseThrow();
        user.getCreatedAudioExcursions().add(audioExcursion);
        userRepository.save(user);
    }

    public void addTour(Long userId, Tour tour) {
        User user = userRepository.findById(userId).orElseThrow();
        user.getCreatedTours().add(tour);
        userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}

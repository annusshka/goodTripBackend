package com.example.goodTripBackend.service;

import com.example.goodTripBackend.models.dto.AccountDto;
import com.example.goodTripBackend.models.dto.AudioTourDto;
import com.example.goodTripBackend.models.dto.TourDto;
import com.example.goodTripBackend.models.entities.AudioFile;
import com.example.goodTripBackend.models.entities.Role;
import com.example.goodTripBackend.models.entities.Tour;
import com.example.goodTripBackend.models.entities.User;
import com.example.goodTripBackend.models.mapper.MapperTour;
import com.example.goodTripBackend.models.mapper.MapperUser;
import com.example.goodTripBackend.repository.AudioFileRepository;
import com.example.goodTripBackend.repository.TourRepository;
import com.example.goodTripBackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    private final TourRepository tourRepository;

    private final AudioFileRepository audioFileRepository;

    private final MapperTour mapperTour;

    private final MapperUser mapperUser;

    public void addLike(Long userId, Long likedTourId) {
        User user = repository.findById(userId).orElseThrow();
        Tour tour = tourRepository.findById(likedTourId).orElseThrow();
        List<Tour> likes = user.getLikes();
        if (likes.contains(tour)) {
            likes.remove(tour);
        } else {
            likes.add(tour);
        }
        repository.save(user);
    }

    public void addTour(Long userId, Long newTourId) {
        User user = repository.findById(userId).orElseThrow();
        Tour tour = tourRepository.findById(newTourId).orElseThrow();
        user.getCreatedTours().add(tour);
        repository.save(user);
    }

    public User findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }

    public List<TourDto> findLikesByUserId(Long id) {
        Optional<User> user = repository.findById(id);
        List<TourDto> tourDtoList = new ArrayList<>();
        if (user.isPresent()) {
            List<Tour> tours = user.get().getLikes();
            for (Tour tour : tours) {
                tourDtoList.add(mapperTour.mapToTourDto(tour, true));
            }
        }
        return tourDtoList;
    }

    public List<AudioTourDto> findCreatedToursByUserId(Long id) {
        Optional<User> user = repository.findById(id);
        List<AudioTourDto> audioTours = null;
        if (user.isPresent()) {
            List<Tour> tours = user.get().getCreatedTours();
            for (Tour tour : tours) {
                AudioFile audioFile = audioFileRepository.findByTour(tour).orElseThrow();
            }
        }
        return audioTours;
    }

    public List<AccountDto> findAll(int offset, int limit) {
        List<User> users = repository.findAll(PageRequest.of(offset, limit)).getContent();
        List<AccountDto> accounts = new ArrayList<>();
        for (User user : users) {
            if (user.getRole() == Role.USER) {
                accounts.add(mapperUser.mapToAccountDto(user));
            }
        }
        return accounts;
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }
}

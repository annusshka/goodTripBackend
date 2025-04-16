package com.example.goodTripBackend.features.tour.service;

import com.example.goodTripBackend.features.tour.models.dto.AudioExcursionDto;
import com.example.goodTripBackend.features.tour.models.dto.AudioTourDto;
import com.example.goodTripBackend.features.tour.models.entities.Address;
import com.example.goodTripBackend.features.tour.models.entities.AudioExcursion;
import com.example.goodTripBackend.features.tour.models.entities.Tour;
import com.example.goodTripBackend.features.tour.models.mapper.MapperAudioExcursion;
import com.example.goodTripBackend.features.tour.models.mapper.MapperAudioTour;
import com.example.goodTripBackend.features.tour.models.mapper.MapperUtils;
import com.example.goodTripBackend.features.tour.repository.AddressRepository;
import com.example.goodTripBackend.features.tour.repository.AudioTourRepository;
import com.example.goodTripBackend.features.user.models.entities.User;
import com.example.goodTripBackend.features.user.models.entities.UserRole;
import com.example.goodTripBackend.features.user.repository.UserRepository;
import com.example.goodTripBackend.features.user.service.UserService;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.models.FileCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AudioTourService {

    private static final String IMAGE_BASE_PATH = "goodTrip/photo";

    private final UserRepository userRepository;

    private final AudioTourRepository audioTourRepository;

    private final AddressRepository addressRepository;

    private final MapperAudioTour mapperAudioTour;

    private final MapperAudioExcursion mapperAudioExcursion;

    private final MapperUtils mapperUtils;

    private final UserService userService;

    public Long save(AudioTourDto tourDto, Long userId) throws Exception {
        List<AudioExcursionDto> audioExcursionDtoList = tourDto.getExcursionList();
        AudioExcursionDto firstAudioExcursionDto = audioExcursionDtoList.get(0);

        var tour = Tour.builder()
                .name(tourDto.getName())
                .weekdays(mapperUtils.mapToWeekdays(tourDto.getWeekdays()))
                .kinds(mapperUtils.mapToTourKinds(tourDto.getKinds()))
                .imagePath(tourDto.getImagePath())
                .description(tourDto.getDescription())
                .audioExcursionList(mapperAudioExcursion.mapToAudioExcursions(audioExcursionDtoList))
                .address(mapperUtils.mapToAddress(firstAudioExcursionDto.getAddress()))
                .build();

        audioTourRepository.save(tour);
        userService.addTour(userId, tour);
        return tour.getId();
    }

    public Long saveFiles(Long tourId, MultipartFile image, Long userId) throws Exception {
        Tour tour = audioTourRepository.findById(tourId).orElseThrow();

        String savedImage = saveImage(image, tour.getId() + "/"+ tour.getImagePath());
        tour.setImagePath(savedImage);

        audioTourRepository.save(tour);
        return tour.getId();
    }

    public String saveImage(MultipartFile imageFile, String id) throws IOException {
        FileCreateRequest fileCreateRequest = new FileCreateRequest(imageFile.getBytes(), IMAGE_BASE_PATH + "/"
                + id);
        try {
            return ImageKit.getInstance().upload(fileCreateRequest).getName();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    public List<AudioTourDto> getAudioToursByCity(String city, Long userId) {
        List<Tour> audioExcursionList = audioTourRepository.findByAddressCity(city);
        List<AudioTourDto> audioExcursionDtos = new ArrayList<>();
        User user = userRepository.findById(userId).orElseThrow();
        List<Tour> likedTours = user.getLikedTours();
        for (Tour audioExcursion : audioExcursionList) {
            boolean isLiked = likedTours.contains(audioExcursion);
            audioExcursionDtos.add(mapperAudioTour.mapToAudioTourDto(audioExcursion, isLiked));
        }
        return audioExcursionDtos;
    }

    public List<AudioTourDto> findAll(int offset, int limit) {
        Page<Tour> audioTourList = audioTourRepository.findAll(PageRequest.of(offset, limit));

        return audioTourList.stream()
                .map(mapperAudioTour::mapToAudioTourDto)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id, Long userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow();
        Tour tour = audioTourRepository.findById(id).orElseThrow();
        boolean isCreatedByUser = user.getCreatedTours().contains(tour);
        if (isCreatedByUser) {
            audioTourRepository.deleteById(id);
        } else {
            throw new Exception();
        }
    }

    public void deleteByAdmin(Long id) {
        audioTourRepository.deleteById(id);
    }
}
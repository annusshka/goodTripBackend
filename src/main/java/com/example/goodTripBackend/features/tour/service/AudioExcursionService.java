package com.example.goodTripBackend.features.tour.service;

import com.example.goodTripBackend.features.tour.models.dto.AudioExcursionDto;
import com.example.goodTripBackend.features.tour.models.entities.AudioExcursion;
import com.example.goodTripBackend.features.tour.models.mapper.MapperAudioExcursion;
import com.example.goodTripBackend.features.tour.repository.AudioExcursionRepository;
import com.example.goodTripBackend.features.user.service.UserService;
import com.example.goodTripBackend.features.user.models.entities.User;
import com.example.goodTripBackend.features.tour.models.mapper.MapperUtils;
import com.example.goodTripBackend.features.user.repository.UserRepository;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.results.Result;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AudioExcursionService {

    private static final String AUDIO_BASE_PATH = "goodTrip/audio";

    private static final String IMAGE_BASE_PATH = "goodTrip/photo";

    private final AudioExcursionRepository audioExcursionRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    private final MapperUtils mapperUtils;

    private final MapperAudioExcursion mapperAudioExcursion;

    public Long save(AudioExcursionDto audioExcursionDto, Long userId) throws Exception {
        String audioPath = audioExcursionDto.getAudioPath();
        String imagePath = audioExcursionDto.getImagePath();

        var audioExcursion = AudioExcursion.builder()
                .name(audioExcursionDto.getName())
                .audioPath(audioPath)
                .imagePath(imagePath)
                .weekdays(mapperUtils.mapToWeekdays(audioExcursionDto.getWeekdays()))
                .kinds(mapperUtils.mapToTourKinds(audioExcursionDto.getKinds()))
                .address(mapperUtils.mapToAddress(audioExcursionDto.getAddress()))
                .description(audioExcursionDto.getDescription())
                .build();

        audioExcursionRepository.save(audioExcursion);
        userService.addExcursion(userId, audioExcursion);
        return audioExcursion.getId();
    }

    public Long saveImageFile(Long excursionId, MultipartFile image) throws Exception {
        AudioExcursion audioExcursion = audioExcursionRepository.findById(excursionId).orElseThrow();

        String savedImage = saveImage(image, audioExcursion.getId() + "/"+ audioExcursion.getImagePath());
        audioExcursion.setImagePath(savedImage);

        audioExcursionRepository.save(audioExcursion);
        return audioExcursion.getId();
    }

    public Long saveAudioFile(Long excursionId, MultipartFile audio) throws Exception {
        AudioExcursion audioExcursion = audioExcursionRepository.findById(excursionId).orElseThrow();

        String savedAudio = saveAudio(audio, audioExcursion.getId() + "/" + audioExcursion.getAudioPath());
        audioExcursion.setAudioPath(savedAudio);

        audioExcursionRepository.save(audioExcursion);
        return audioExcursion.getId();
    }

    public String saveImage(MultipartFile imageFile, String id) throws IOException {
        FileCreateRequest fileCreateRequest = new FileCreateRequest(imageFile.getBytes(), IMAGE_BASE_PATH + "/" + id);
        try {
            return ImageKit.getInstance().upload(fileCreateRequest).getName();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String saveAudio(MultipartFile audioFile, String id) throws IOException {
        FileCreateRequest fileCreateRequest = new FileCreateRequest(audioFile.getBytes(), AUDIO_BASE_PATH + "/" + id);
        try {
            Result result = ImageKit.getInstance().upload(fileCreateRequest);
            return result.getName();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<AudioExcursionDto> findAudioExcursionLikesByUserId(Long id) throws Exception {
        User user = userRepository.findById(id).orElseThrow();
        List<AudioExcursionDto> audioExcursionDtoList = new ArrayList<>();
        List<AudioExcursion> audioExcursionList = user.getLikedAudioExcursions();
        for (AudioExcursion audioExcursion : audioExcursionList) {
            audioExcursionDtoList.add(mapperAudioExcursion.mapToAudioExcursionDto(audioExcursion, true));
        }
        return audioExcursionDtoList;
    }

    public void addAudioExcursionLike(Long userId, Long likedAudioExcursionId, boolean isLiked) throws Exception {
        User user = userRepository.findById(userId).orElseThrow();
        AudioExcursion audioExcursion = audioExcursionRepository.findById(likedAudioExcursionId).orElseThrow();
        List<AudioExcursion> likes = user.getLikedAudioExcursions();
        if (isLiked) {
            if (!likes.contains(audioExcursion)) {
                likes.add(audioExcursion);
            }
        } else {
            likes.remove(audioExcursion);
        }
        user.setLikedAudioExcursions(likes);
        userRepository.save(user);
    }

    public List<AudioExcursionDto> findCreatedAudioExcursions(Long userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow();
        List<AudioExcursionDto> audioExcursionDtos = new ArrayList<>();
        List<AudioExcursion> createdTourList = user.getCreatedAudioExcursions();
        List<AudioExcursion> audioExcursionList = user.getLikedAudioExcursions();
        for (AudioExcursion audioExcursion : createdTourList) {
            boolean isLiked = audioExcursionList.contains(audioExcursion);
            audioExcursionDtos.add(mapperAudioExcursion.mapToAudioExcursionDto(audioExcursion, isLiked));
        }
        return audioExcursionDtos;
    }

//    public boolean checkIsLiked(Long userId, Long tourId) throws Exception {
//        User user = userService.findById(userId);
//        AudioExcursion audioExcursion = audioExcursionRepository.findById(tourId).orElseThrow();
//        return user.getLikedAudioExcursions().contains(audioExcursion);
//    }
//
//    public AudioExcursionDto findById(Long userId, Long id) throws Exception {
//        AudioExcursion audioExcursion = audioExcursionRepository.findById(id).orElseThrow();
//        boolean isLiked = checkIsLiked(userId, id);
//
//        return mapperAudioExcursion.mapToAudioExcursionDto(audioExcursion, isLiked);
//    }

    public List<AudioExcursionDto> findAll(int offset, int limit) {
        Page<AudioExcursion> audioExcursionList = audioExcursionRepository.findAll(PageRequest.of(offset, limit));

        return audioExcursionList.stream()
                .map(mapperAudioExcursion::mapToAudioExcursionDto)
                .collect(Collectors.toList());
    }

//    public List<AudioExcursionDto> findAllByUser(Long userId, int offset, int limit) throws Exception {
//        User user = userService.findById(userId);
//        List<AudioExcursion> audioExcursionList = user.getCreatedAudioExcursions();
//        List<AudioExcursionDto> audioExcursionDtoList = new ArrayList<>();
//        for (AudioExcursion audioExcursion : audioExcursionList) {
//            boolean isLiked = checkIsLiked(userId, audioExcursion.getId());
//            AudioExcursionDto audioTour = mapperAudioExcursion.mapToAudioExcursionDto(audioExcursion, isLiked);
//            audioExcursionDtoList.add(audioTour);
//        }
//        return audioExcursionDtoList;
//    }

    public List<AudioExcursionDto> getAudioExcursionsByCity(String city, Long userId) {
        List<AudioExcursion> audioExcursionList = audioExcursionRepository.findByAddressCity(city);
        List<AudioExcursionDto> audioExcursionDtos = new ArrayList<>();
        User user = userRepository.findById(userId).orElseThrow();
        List<AudioExcursion> likedAudioExcursions = user.getLikedAudioExcursions();
        for (AudioExcursion audioExcursion : audioExcursionList) {
            boolean isLiked = likedAudioExcursions.contains(audioExcursion);
            audioExcursionDtos.add(mapperAudioExcursion.mapToAudioExcursionDto(audioExcursion, isLiked));
        }
        return audioExcursionDtos;
    }

    public void deleteById(Long userId, Long id) throws Exception {
        User user = userService.findById(userId);
        List<AudioExcursion> audioExcursionList = user.getCreatedAudioExcursions();
        AudioExcursion excursion = audioExcursionRepository.findById(id).orElseThrow();
        boolean isCreatedByUser = audioExcursionList.contains(excursion);
        if (isCreatedByUser) {
            audioExcursionList.remove(excursion);
            user.setCreatedAudioExcursions(audioExcursionList);
            userRepository.save(user);
        } else {
            throw new Exception();
        }
    }
}
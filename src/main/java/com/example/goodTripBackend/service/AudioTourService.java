package com.example.goodTripBackend.service;

import com.example.goodTripBackend.models.dto.AudioTourDto;
import com.example.goodTripBackend.models.entities.Address;
import com.example.goodTripBackend.models.entities.AudioFile;
import com.example.goodTripBackend.models.entities.Tour;
import com.example.goodTripBackend.models.entities.User;
import com.example.goodTripBackend.models.mapper.MapperAudioTour;
import com.example.goodTripBackend.models.mapper.MapperUtils;
import com.example.goodTripBackend.repository.AudioFileRepository;
import com.example.goodTripBackend.repository.UserRepository;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.models.FileCreateRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AudioTourService {

    private static final String AUDIO_BASE_PATH = "goodTrip/audio";

    private static final String IMAGE_BASE_PATH = "goodTrip/photo";

    private final AudioFileRepository audioFileRepository;

    private final UserService userService;

    private final TourService tourService;

    private final MapperUtils mapperUtils;

    private final MapperAudioTour mapperAudioTour;
    private final UserRepository userRepository;

    public Long saveTour(AudioTourDto audioTour, Long userId) {
        Tour tour = Tour.builder()
                .name(audioTour.getName())
                .weekdays(mapperUtils.mapToWeekdays(audioTour.getWeekdays()))
                .description(audioTour.getDescription())
                .build();

        Address address = mapperUtils.mapToAddress(audioTour.getAddress());
        address.setTour(tour);
        tour.setAddress(address);
        tour.setKinds(mapperUtils.mapToTourKinds(audioTour.getKinds()));

        tourService.save(tour);
        userService.addTour(userId, tour.getId());
        return tour.getId();
    }

    public Long saveFiles(Long tourId, MultipartFile image, MultipartFile audio) throws IOException {
        Tour tour = tourService.findById(tourId);
        tour.setImagePath(saveImage(image, String.valueOf(tour.getId())));
        String audioPath = saveAudio(audio, tourId);

        AudioFile audioTour = AudioFile.builder()
                .audioPath(audioPath)
                .tour(tour)
                .build();
        audioFileRepository.save(audioTour);
        return tourService.save(tour);
    }

    public Long saveImageFile(Long tourId, MultipartFile image) throws IOException {
        Tour tour = tourService.findById(tourId);
        tour.setImagePath(saveImage(image, String.valueOf(tour.getId())));
        return tourService.save(tour);
    }

    public Long saveAudioFile(Long tourId, MultipartFile audio) throws IOException {
        Tour tour = tourService.findById(tourId);
        String audioPath = saveAudio(audio, tourId);
        AudioFile audioTour = AudioFile.builder()
                .audioPath(audioPath)
                .tour(tour)
                .build();
        audioFileRepository.save(audioTour);
        return tourService.save(tour);
    }

    public Long save(AudioTourDto audioTour, MultipartFile image, MultipartFile audio, Long userId) throws IOException {
        var tour = Tour.builder()
                .name(audioTour.getName())
                .weekdays(mapperUtils.mapToWeekdays(audioTour.getWeekdays()))
                .description(audioTour.getDescription())
                .build();
        tour.setImagePath(saveImage(image, String.valueOf(tour.getId())));

        var address = mapperUtils.mapToAddress(audioTour.getAddress());
        address.setTour(tour);

        tour.setKinds(mapperUtils.mapToTourKinds(audioTour.getKinds()));

        saveAudio(audio, tour.getId());
        tourService.save(tour);
        userService.addTour(userId, tour.getId());
        return tour.getId();
    }

    public String saveImage(MultipartFile imageFile, String id) throws IOException {
        FileCreateRequest fileCreateRequest = new FileCreateRequest(imageFile.getBytes(), IMAGE_BASE_PATH + "/"
                + id + imageFile.getOriginalFilename());
        try {
            return ImageKit.getInstance().upload(fileCreateRequest).getName();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String saveAudio(MultipartFile audioFile, Long id) throws IOException {
        FileCreateRequest fileCreateRequest = new FileCreateRequest(audioFile.getBytes(), AUDIO_BASE_PATH + "/"
                + id + audioFile.getOriginalFilename());
        try {
            return ImageKit.getInstance().upload(fileCreateRequest).getName();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkIsLiked(Long userId, Long tourId) {
        User user = userService.findById(userId);
        Tour tour = tourService.findById(tourId);
        return user.getLikes().contains(tour);
    }

    public AudioTourDto findById(Long userId, Long id) {
        Tour tour = tourService.findById(id);
        AudioFile audioFile = audioFileRepository.findByTour(tour).orElseThrow();
        boolean isLiked = checkIsLiked(userId, id);

        return mapperAudioTour.mapToAudioTourDto(tour, audioFile, isLiked);
    }

    public List<AudioFile> findAllAudioFiles(int offset, int limit) {
        return audioFileRepository.findAll(PageRequest.of(offset, limit)).getContent();
    }

    public List<AudioTourDto> findAll(int offset, int limit) {
        List<AudioFile> audioFiles = findAllAudioFiles(offset, limit);
        List<AudioTourDto> audioTours = new ArrayList<>();
        for (AudioFile audioFile : audioFiles) {
            Tour tour = tourService.findById(audioFile.getId());
            AudioTourDto audioTour = mapperAudioTour.mapToAudioTourDto(tour, audioFile);
            audioTours.add(audioTour);
        }
        return audioTours;
    }

    public List<AudioTourDto> findAllByUser(Long userId, int offset, int limit) {
        User user = userService.findById(userId);
        List<Tour> tours = user.getCreatedTours();
        List<AudioTourDto> audioTours = new ArrayList<>();
        for (Tour tour : tours) {
            AudioFile audioFile = audioFileRepository.findByTour(tour).orElseThrow();
            boolean isLiked = checkIsLiked(userId, tour.getId());
            AudioTourDto audioTour = mapperAudioTour.mapToAudioTourDto(tour, audioFile, isLiked);
            audioTours.add(audioTour);
        }
//        List<AudioFile> audioFiles = findAllAudioFiles(offset, limit);
//        List<AudioTourDto> audioTours = new ArrayList<>();
//        for (AudioFile audioFile : audioFiles) {
//            Tour tour = tourService.findById(audioFile.getId());
//            boolean isLiked = checkIsLiked(userId, tour.getId());
//            AudioTourDto audioTour = mapperAudioTour.mapToAudioTourDto(tour, audioFile, isLiked);
//            audioTours.add(audioTour);
//        }
        return audioTours;
    }

    public List<AudioTourDto> findAllByCity(String city, Long userId, int offset, int limit) {
        List<AudioTourDto> audioTours = new ArrayList<>();
        List<Tour> tours = tourService.findAllByCity(city, offset, limit);
        for (Tour tour : tours) {
            Optional<AudioFile> audioFile = audioFileRepository.findById(tour.getId());
            boolean isLiked = checkIsLiked(userId, tour.getId());
            audioFile.ifPresent(file -> audioTours.add(mapperAudioTour.mapToAudioTourDto(tour, file, isLiked)));
        }
        return audioTours;
    }

    public void deleteById(Long userId, Long id) {
        User user = userService.findById(userId);
        List<Tour> tours = user.getCreatedTours();
        for (Tour tour : tours) {
            if (tour.getId().equals(id)) {
                AudioFile audioFile = audioFileRepository.findByTour(tour).orElseThrow();
                audioFileRepository.delete(audioFile);
                tourService.delete(tour.getId());
                user.getCreatedTours().remove(tour);
                userRepository.save(user);
            }
        }
//        Tour tour = tourService.findById(id);
//        AudioFile audioFile = audioFileRepository.findByTour(tour).orElseThrow();
//        audioFileRepository.delete(audioFile);
//        tourService.delete(tour.getId());
    }
}

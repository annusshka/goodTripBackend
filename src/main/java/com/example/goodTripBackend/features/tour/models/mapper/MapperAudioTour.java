package com.example.goodTripBackend.features.tour.models.mapper;

import com.example.goodTripBackend.features.tour.models.dto.AudioTourDto;
import com.example.goodTripBackend.features.tour.models.entities.AudioFile;
import com.example.goodTripBackend.features.tour.models.entities.Tour;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MapperAudioTour {

    private final MapperUtils mapperUtils;

    public AudioTourDto mapToAudioTourDto(Tour tour) {
        return AudioTourDto.builder()
                .id(tour.getId())
                .name(tour.getName())
                .kinds(mapperUtils.mapToStringKinds(tour.getKinds()))
                .address(mapperUtils.mapToAddressDto(tour.getAddress()))
                .imagePath(tour.getImagePath())
                .description(tour.getDescription())
                .build();
    }

    public AudioTourDto mapToAudioTourDto(Tour tour, AudioFile audioFile) {
        var audioTourDto = mapToAudioTourDto(tour);
        audioTourDto.setAudioPath(audioFile.getAudioPath());
        return audioTourDto;
    }

    public AudioTourDto mapToAudioTourDto(Tour tour, AudioFile audioFile, boolean isLiked) {
        var audioTourDto = mapToAudioTourDto(tour, audioFile);
        audioTourDto.setLiked(isLiked);
        return audioTourDto;
    }

    public AudioTourDto mapToAudioTourDto(Tour tour, String audioFilePath) {
        var audioTourDto = mapToAudioTourDto(tour);
        audioTourDto.setAudioPath(audioFilePath);
        return audioTourDto;
    }

    public Tour mapToTour(AudioTourDto audioTourDto) {
        return Tour.builder()
                .id(audioTourDto.getId())
                .name(audioTourDto.getName())
                .kinds(mapperUtils.mapToTourKinds(audioTourDto.getKinds()))
                .address(mapperUtils.mapToAddress(audioTourDto.getAddress()))
                .imagePath(audioTourDto.getImagePath())
                .description(audioTourDto.getDescription())
                .build();
    }
}

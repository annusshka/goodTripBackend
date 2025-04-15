package com.example.goodTripBackend.features.tour.models.mapper;

import com.example.goodTripBackend.features.tour.models.dto.AudioTourDto;
import com.example.goodTripBackend.features.tour.models.entities.Tour;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MapperAudioTour {

    private final MapperUtils mapperUtils;

    private final MapperAudioExcursion mapperAudioExcursion;

    public AudioTourDto mapToAudioTourDto(Tour tour) {
        return AudioTourDto.builder()
                .id(tour.getId())
                .name(tour.getName())
                .kinds(mapperUtils.mapToStringKinds(tour.getKinds()))
                .weekdays(mapperUtils.mapToWeekdayDtos(tour.getWeekdays()))
                .address(mapperUtils.mapToAddressDto(tour.getAddress()))
                .imagePath(tour.getImagePath())
                .description(tour.getDescription())
                .excursionList(mapperAudioExcursion.mapToAudioExcursionDtos(tour.getAudioExcursionList()))
                .build();
    }

    public AudioTourDto mapToAudioTourDto(Tour tour, boolean isLiked) {
        var audioTourDto = mapToAudioTourDto(tour);
        audioTourDto.setLiked(isLiked);
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
                .audioExcursionList(mapperAudioExcursion.mapToAudioExcursions(audioTourDto.getExcursionList()))
                .build();
    }
}

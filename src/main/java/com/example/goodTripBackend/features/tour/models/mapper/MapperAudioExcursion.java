package com.example.goodTripBackend.features.tour.models.mapper;

import com.example.goodTripBackend.features.tour.models.dto.AudioExcursionDto;
import com.example.goodTripBackend.features.tour.models.entities.AudioExcursion;
import com.example.goodTripBackend.features.tour.models.entities.Weekday;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MapperAudioExcursion {

    private final MapperUtils mapperUtils;

    public AudioExcursionDto mapToAudioExcursionDto(AudioExcursion audioExcursion) {
        return AudioExcursionDto.builder()
                .id(audioExcursion.getId())
                .name(audioExcursion.getName())
                .imagePath(audioExcursion.getImagePath())
                .audioPath(audioExcursion.getAudioPath())
                .weekdays(mapperUtils.mapToWeekdayDtos(audioExcursion.getWeekdays()))
                .kinds(mapperUtils.mapToStringKinds(audioExcursion.getKinds()))
                .address(mapperUtils.mapToAddressDto(audioExcursion.getAddress()))
                .description(audioExcursion.getDescription())
                .build();
    }

    public AudioExcursionDto mapToAudioExcursionDto(AudioExcursion audioExcursion, boolean isLiked) {
        var audioExcursionDto = mapToAudioExcursionDto(audioExcursion);
        audioExcursionDto.setLiked(isLiked);
        return audioExcursionDto;
    }

    public AudioExcursionDto mapToAudioExcursionDto(AudioExcursion audioExcursion, String audioFilePath) {
        var audioExcursionDto = mapToAudioExcursionDto(audioExcursion);
        audioExcursionDto.setAudioPath(audioFilePath);
        return audioExcursionDto;
    }

    public AudioExcursion mapToAudioExcursion(AudioExcursionDto audioTourDto) {
        return AudioExcursion.builder()
                .id(audioTourDto.getId())
                .name(audioTourDto.getName())
                .kinds(mapperUtils.mapToTourKinds(audioTourDto.getKinds()))
                .address(mapperUtils.mapToAddress(audioTourDto.getAddress()))
                .imagePath(audioTourDto.getImagePath())
                .audioPath(audioTourDto.getAudioPath())
                .description(audioTourDto.getDescription())
                .build();
    }

    public List<AudioExcursion> mapToAudioExcursions(List<AudioExcursionDto> audioExcursionDtoList) {
        List<AudioExcursion> audioExcursionList = new ArrayList<>();
        for (AudioExcursionDto audioExcursionDto : audioExcursionDtoList) {
            audioExcursionList.add(mapToAudioExcursion(audioExcursionDto));
        }

        return audioExcursionList;
    }

    public List<AudioExcursionDto> mapToAudioExcursionDtos(List<AudioExcursion> audioExcursionList) {
        List<AudioExcursionDto> audioExcursionDtoList = new ArrayList<>();
        for (AudioExcursion audioExcursion : audioExcursionList) {
            audioExcursionDtoList.add(mapToAudioExcursionDto(audioExcursion));
        }

        return audioExcursionDtoList;
    }
}
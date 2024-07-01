package com.example.goodTripBackend.models.mapper;

import com.example.goodTripBackend.models.dto.TourDto;
import com.example.goodTripBackend.models.entities.Tour;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MapperTour {

    private final MapperUtils mapperUtils;

    public TourDto mapToTourDto(Tour tour) {
        return TourDto.builder()
                .id(tour.getId())
                .name(tour.getName())
                .kinds(mapperUtils.mapToStringKinds(tour.getKinds()))
                .address(mapperUtils.mapToAddressDto(tour.getAddress()))
                .imagePath(tour.getImagePath())
                .description(tour.getDescription())
                .build();
    }

    public TourDto mapToTourDto(Tour tour, boolean isLiked) {
        TourDto tourDto = mapToTourDto(tour);
        tourDto.setLiked(isLiked);
        return tourDto;
    }

    public Tour mapToTour(TourDto tourDto) {
        return Tour.builder()
                .id(tourDto.getId())
                .name(tourDto.getName())
                .kinds(mapperUtils.mapToTourKinds(tourDto.getKinds()))
                .address(mapperUtils.mapToAddress(tourDto.getAddress()))
                .imagePath(tourDto.getImagePath())
                .description(tourDto.getDescription())
                .build();
    }
}

package com.example.goodTripBackend.features.tour.models.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AudioExcursionDto {

    private Long id;

    private String name;

    private String imagePath;

    private String audioPath;

    private List<String> weekdays;

    private AddressDto address;

    private String description;

    private List<String> kinds;

    @Builder.Default
    private boolean isLiked = false;
}
package com.example.goodTripBackend.features.tour.models.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AudioTourDto {

    private Long id;

    private String name;

    private String imagePath;

    private List<String> weekdays;

    private AddressDto address;

    private String description;

    private List<String> kinds;

    @Builder.Default
    private boolean isLiked = false;
}

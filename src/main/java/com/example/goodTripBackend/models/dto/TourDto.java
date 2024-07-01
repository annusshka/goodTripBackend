package com.example.goodTripBackend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TourDto {

    private Long id;

    private String name;

    private String imagePath;

    private List<String> weekdays;

    private AddressDto address;

    private String description;

    private List<String> kinds;

    private boolean isLiked = false;
}


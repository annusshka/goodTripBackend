package com.example.goodTripBackend.features.tour.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {

    private String country;

    private String city;

    private String street;

    private String house;

    private double lat;

    private double lon;
}

package com.example.goodTripBackend.features.tour.models.mapper;

import com.example.goodTripBackend.features.tour.models.dto.AddressDto;
import com.example.goodTripBackend.features.tour.models.entities.Address;
import com.example.goodTripBackend.features.tour.models.entities.TourKind;
import com.example.goodTripBackend.features.tour.models.entities.Weekday;
import com.example.goodTripBackend.features.tour.repository.TourKindRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MapperUtils {

    private final TourKindRepository tourKindRepository;

    public List<TourKind> mapToTourKinds(List<String> kinds) {
        List<TourKind> tourKinds = new ArrayList<>();
        for (String kind : kinds) {
            TourKind tourKind = tourKindRepository.findByKind(kind).orElseThrow();
            tourKinds.add(tourKind);
        }

        return tourKinds;
    }

    public List<String> mapToStringKinds(List<TourKind> kinds) {
        List<String> tourKinds = new ArrayList<>();
        for (TourKind kind : kinds) {
            tourKinds.add(kind.getKind());
        }

        return tourKinds;
    }

    public AddressDto mapToAddressDto(Address address) {
        return AddressDto.builder()
                .country(address.getCountry())
                .city(address.getCity())
                .street(address.getStreet())
                .house(address.getHouse())
                .lat(address.getLat())
                .lon(address.getLon())
                .build();
    }

    public Address mapToAddress(AddressDto addressDto) {
        String country = addressDto.getCountry();
        String city = addressDto.getCity();
        String street = addressDto.getStreet();
        String house = addressDto.getHouse();
        double lat = addressDto.getLat();
        double lon = addressDto.getLon();

        return Address.builder()
                .country(country)
                .city(city)
                .street(street == null || street.isEmpty() ? null : street)
                .house(house == null || house.isEmpty() ? null : house)
                .lat(lat)
                .lon(lon)
                .build();
    }

    public List<Weekday> mapToWeekdays(List<String> weekdayStringList) {
        List<Weekday> weekdays = new ArrayList<>();
        for (String day : weekdayStringList) {
            weekdays.add(Weekday.valueOf(day.toUpperCase()));
        }

        return weekdays;
    }

    public List<String> mapToWeekdayDtos(List<Weekday> weekdays) {
        List<String> weekdayDtos = new ArrayList<>();
        for (Weekday weekday : weekdays) {
            weekdayDtos.add(weekday.name().toLowerCase());
        }

        return weekdayDtos;
    }
}

package com.example.goodTripBackend.features.tour.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tour_address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "house")
    private String house;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JsonIgnore
    private Tour tour;
}

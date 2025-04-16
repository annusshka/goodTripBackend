package com.example.goodTripBackend.features.tour.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tour")
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tour_id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "image_path")
    private String imagePath;

    @Enumerated(EnumType.STRING)
    private List<Weekday> weekdays;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "tour_address",
            joinColumns = @JoinColumn(name = "tour_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    private Address address;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "tour_kind",
            joinColumns = @JoinColumn(name = "tour_id"),
            inverseJoinColumns = @JoinColumn(name = "kind_id"))
    private List<TourKind> kinds;

    @ManyToMany
    @JoinTable(
            name = "tour_audio_excursion",
            joinColumns = @JoinColumn(name = "tour_id"),
            inverseJoinColumns = @JoinColumn(name = "audio_excursion_id"))
    private List<AudioExcursion> audioExcursionList;
}

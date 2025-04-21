package com.example.goodTripBackend.features.tour.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "audio_excursion")
public class AudioExcursion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "audio_excursion_id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "audio_path")
    private String audioPath;

    @Enumerated(EnumType.STRING)
    private List<Weekday> weekdays;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "audio_excursion_address",
            joinColumns = @JoinColumn(name = "audio_excursion_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    private Address address;

    @Column(name = "description")
    private String description;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "audio_excursion_kind",
            joinColumns = @JoinColumn(name = "audio_excursion_id"),
            inverseJoinColumns = @JoinColumn(name = "kind_id"))
    private List<TourKind> kinds;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name="user_id", nullable = false)
//    @JsonIgnore
//    private User user;
}

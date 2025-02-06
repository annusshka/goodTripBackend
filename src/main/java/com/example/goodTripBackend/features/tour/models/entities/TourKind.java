package com.example.goodTripBackend.features.tour.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "kinds")
public class TourKind {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kind_id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "kind")
    private String kind;
}

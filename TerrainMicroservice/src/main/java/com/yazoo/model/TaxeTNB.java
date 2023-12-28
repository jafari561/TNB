package com.yazoo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

@Transactional
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxeTNB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double tauxTerrain;
    private double surface;
    private int annee;
    private double montantPaye;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinColumn(name = "terrain_id")
    @JsonProperty("terrain")
    private Terrain terrain;

    // Getters and setters
}



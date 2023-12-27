package com.yazoo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

import java.util.List;

@Transactional
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Terrain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long terrainID;
    private double surface;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "categorie_id")
    private CategorieTerrain categorie;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "proprietaire_cin")
    private Redevable proprietaire;

    @JsonIgnore
    @OneToMany(mappedBy = "terrain", cascade = CascadeType.ALL)
    private List<TaxeTNB> taxesTNB;

    // Getters and setters
}

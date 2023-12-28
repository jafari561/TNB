package com.yazoo.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Transactional
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Terrain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long terrainID;
    private double surface;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private CategorieTerrain categorie;

    @ManyToOne
    @JoinColumn(name = "proprietaire_cin")
    private Redevable proprietaire;

    @OneToMany(mappedBy = "terrain", cascade = CascadeType.ALL)
    private List<TaxeTNB> taxesTNB;

    // Getters and setters
}

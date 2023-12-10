package com.yazoo.categorieterrainmicroservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private CategorieTerrain categorie;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "proprietaire_cin")
    private Redevable proprietaire;

    @OneToMany(mappedBy = "terrain", cascade = CascadeType.ALL)
    private List<TaxeTNB> taxesTNB;

}

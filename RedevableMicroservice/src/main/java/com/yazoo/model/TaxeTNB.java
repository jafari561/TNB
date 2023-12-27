package com.yazoo.model;


import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Transactional
@Entity
@Getter
@Setter
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

    @ManyToOne
    @JoinColumn(name = "terrain_id")
    private Terrain terrain;

    // Getters and setters
}



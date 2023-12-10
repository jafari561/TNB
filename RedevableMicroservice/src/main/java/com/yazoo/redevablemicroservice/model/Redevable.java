package com.yazoo.redevablemicroservice.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class Redevable {
    @Id
    private String cin;
    private String nom;
    private String prenom;
    private String adresse;

    @OneToMany(mappedBy = "proprietaire", cascade = CascadeType.ALL)
    private List<Terrain> terrains;


}
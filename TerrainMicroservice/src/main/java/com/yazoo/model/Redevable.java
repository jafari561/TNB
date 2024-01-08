package com.yazoo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class Redevable {
    @Id
    private String cin;
    private String nom;
    private String prenom;
    private String adresse;

    @JsonIgnore
    @OneToMany(mappedBy = "proprietaire")
    private List<Terrain> terrains;

    // Getters and setters
}
package com.yazoo.categorieterrainmicroservice.repository;

import com.yazoo.categorieterrainmicroservice.model.CategorieTerrain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieTerrainRepository extends JpaRepository<CategorieTerrain, Long> {
    // Add any additional queries if needed
    CategorieTerrain findByNomCategorie(String nomCategorie);
}


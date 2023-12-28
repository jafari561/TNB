package com.yazoo.repository;

import com.yazoo.model.CategorieTerrain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieTerrainRepository extends JpaRepository<CategorieTerrain, Long> {
    // Add any additional queries if needed
    CategorieTerrain findByNomCategorie(String nomCategorie);
}


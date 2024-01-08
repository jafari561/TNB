package com.yazoo.repository;

import com.yazoo.model.Redevable;
import com.yazoo.model.Terrain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TerrainRepository extends JpaRepository<Terrain, Long> {
    // Add any additional queries if needed

    List<Terrain> findTerrainByProprietaire(Redevable redevable);
}


package com.yazoo.terrainmicroservice.repository;

import com.yazoo.terrainmicroservice.model.Terrain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TerrainRepository extends JpaRepository<Terrain, Long> {
    // Add any additional queries if needed
}


package com.yazoo.repository;

import com.yazoo.model.Terrain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TerrainRepository extends JpaRepository<Terrain, Long> {
    // Add any additional queries if needed
}


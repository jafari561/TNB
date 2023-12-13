package com.yazoo.terrainmicroservice.dto;

import com.yazoo.terrainmicroservice.model.Terrain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TerrainRequest {

    private Terrain terrain;
}

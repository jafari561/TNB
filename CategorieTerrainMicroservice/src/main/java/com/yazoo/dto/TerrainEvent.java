package com.yazoo.dto;

import com.yazoo.model.Terrain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TerrainEvent {

    private String eventType;
    private Terrain terrain;
}

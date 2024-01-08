package com.yazoo.controller;

import com.yazoo.service.TerrainService;
import com.yazoo.dto.TerrainRequest;
import com.yazoo.model.Terrain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/terrain")
public class TerrainController {
    private final TerrainService terrainService;

    @Autowired
    public TerrainController(TerrainService terrainService) {
        this.terrainService = terrainService;
    }

    @GetMapping("/all")
    public List<Terrain> getAllTerrains() {
        return terrainService.getAllTerrains();
    }

    @PostMapping("/save")
    public String saveTerrain(@RequestBody TerrainRequest request) {
        Terrain terrain = request.getTerrain();
        return terrainService.saveTerrain(terrain);
    }

    @PutMapping("/{terrainId}")
    public String updateTerrain(@PathVariable Long terrainId, @RequestBody TerrainRequest terrainRequest){
        return terrainService.updateTerrain(terrainId,terrainRequest);
    }

    @GetMapping("/{id}")
    public Terrain getTerrainById(@PathVariable Long id) {
        return terrainService.getTerrainById(id);
    }

    @GetMapping("/{terrainId}/calculate-tax")
    public double calculateNonBuiltLandTax(@PathVariable Long terrainId) {
        return terrainService.calculateTax(terrainId);
    }

    @GetMapping("/{terrainId}/is-tax-paid")
    public boolean isTaxPaidForYear(@PathVariable Long terrainId, @RequestParam int year) {
        return terrainService.isTaxPaidForYear(terrainId, year);
    }

    @GetMapping("/proprietaire/{cin}")
    public List<Terrain> findTerrainsByCIN(@PathVariable String cin) {
        return terrainService.findTerrainsOfRedevable(cin);
    }

    // Additional endpoints for CRUD operations and other functionalities
}


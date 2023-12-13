package com.yazoo.terrainmicroservice.controller;

import com.yazoo.terrainmicroservice.dto.TerrainRequest;
import com.yazoo.terrainmicroservice.model.Terrain;
import com.yazoo.terrainmicroservice.service.TerrainService;
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

    // Additional endpoints for CRUD operations and other functionalities
}


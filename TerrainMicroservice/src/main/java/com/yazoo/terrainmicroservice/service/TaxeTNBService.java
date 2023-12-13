package com.yazoo.terrainmicroservice.service;


import com.yazoo.terrainmicroservice.model.TaxeTNB;
import com.yazoo.terrainmicroservice.model.Terrain;
import com.yazoo.terrainmicroservice.repository.TaxeTNBRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TaxeTNBService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private TerrainService terrainService;

    private final TaxeTNBRepository taxeTNBRepository;
    @Autowired
    public TaxeTNBService(TaxeTNBRepository taxeTNBRepository) {
        this.taxeTNBRepository = taxeTNBRepository;
    }

    //==================================================================================================================
    public List<TaxeTNB> getAllTaxes() {
        return taxeTNBRepository.findAll();
    }

    public TaxeTNB saveTax(TaxeTNB taxeTNB) {
        // Additional logic for saving a tax
        Terrain terrain = terrainService.getTerrainById(taxeTNB.getTerrain().getTerrainID());
        validateTerrainExistence(terrain.getTerrainID());

        taxeTNB.setTerrain(terrain);
        return taxeTNBRepository.save(taxeTNB);

    }

    public List<TaxeTNB> getHistoricalTaxesForTerrain(Long terrainId) {
        // Validate existence of terrain using WebClient
        validateTerrainExistence(terrainId);

        // Continue with retrieving historical taxes
        return taxeTNBRepository.findByTerrainTerrainID(terrainId);
    }

    // Additional methods for CRUD operations and other functionalities

    private void validateTerrainExistence(Long terrainId) {
        Terrain terrainById = terrainService.getTerrainById(terrainId);
        if(terrainById==null){throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Terrain with ID " + terrainId + " not found.");}
    }

    public TaxeTNB findByTerrainAndAnnee(Terrain terrain, int annee) {
        return taxeTNBRepository.findByTerrainAndAnnee(terrain, annee);
    }
}


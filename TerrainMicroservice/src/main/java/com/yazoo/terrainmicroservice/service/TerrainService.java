package com.yazoo.terrainmicroservice.service;

import com.yazoo.terrainmicroservice.errors.EntityNotFoundException;
import com.yazoo.terrainmicroservice.model.CategorieTerrain;
import com.yazoo.terrainmicroservice.model.Redevable;
import com.yazoo.terrainmicroservice.model.TaxeTNB;
import com.yazoo.terrainmicroservice.model.Terrain;
import com.yazoo.terrainmicroservice.repository.TerrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TerrainService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private TaxeTNBService taxeTNBService;

    private final TerrainRepository terrainRepository;

    @Autowired
    public TerrainService(TerrainRepository terrainRepository) {
        this.terrainRepository = terrainRepository;
    }

    //=====================================================================================
    public List<Terrain> getAllTerrains() {
        return terrainRepository.findAll();
    }

    public String saveTerrain(Terrain terrain) {
        // Additional logic for saving a terrain
        if (terrain.getSurface() <= 0) {
            return "surface can not be negative ";
        } else {
            try {
                Redevable redevable = restTemplate.getForObject("http://REDEVABLE-SERVICE/redevable/" + terrain.getProprietaire().getCin(), Redevable.class);
                CategorieTerrain categorie = restTemplate.getForObject("http://CATEGORIE-SERVICE/categorie/nom/" + terrain.getCategorie().getNomCategorie(), CategorieTerrain.class);
                terrain.setProprietaire(redevable);
                terrain.setCategorie(categorie);
                terrainRepository.save(terrain);
                return "terrain added";
            } catch (Exception e) {
                // Handle exceptions, log, or perform other error handling
                e.printStackTrace();
                return e.getMessage();
            }
        }
    }

    public Terrain getTerrainById(Long id) {
        return terrainRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Terrain with ID " + id + " not found."));
    }

    public double calculateTax(Long terrainId) {
        // Implement logic to calculate tax based on terrainId
        Optional<Terrain> terrain = terrainRepository.findById(terrainId);
        CategorieTerrain categorie = restTemplate.getForObject("http://CATEGORIE-SERVICE/categorie/nom/" + terrain.get().getCategorie().getNomCategorie(), CategorieTerrain.class);

        return terrain.get().getSurface() * categorie.getTaux();  // Placeholder, replace with actual calculation
    }

    public boolean isTaxPaidForYear(Long terrainId, int year) {
        // Implement logic to check if the tax for the specified year is paid
        Terrain terrain = terrainRepository.findById(terrainId)
                .orElseThrow(()-> new EntityNotFoundException("terrain with ID " + terrainId + " not found"));
        TaxeTNB taxRecords = taxeTNBService.findByTerrainAndAnnee(terrain,year);
        if(taxRecords == null) {return false;} else {return true;}
    }

    // Additional methods for CRUD operations and other functionalities
}


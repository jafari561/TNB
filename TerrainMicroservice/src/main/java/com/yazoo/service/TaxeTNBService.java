package com.yazoo.service;


import com.yazoo.dto.TaxeRequest;
import com.yazoo.model.TaxeTNB;
import com.yazoo.model.Terrain;
import com.yazoo.repository.TaxeTNBRepository;
import com.yazoo.repository.TerrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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
    private final TerrainRepository terrainRepository;
    @Autowired
    public TaxeTNBService(TaxeTNBRepository taxeTNBRepository, TerrainRepository terrainRepository) {
        this.taxeTNBRepository = taxeTNBRepository;
        this.terrainRepository = terrainRepository;
    }

    //==================================================================================================================
    public List<TaxeTNB> getAllTaxes() {
        return taxeTNBRepository.findAll();
    }

    public String saveTax(TaxeRequest taxeRequest) {
        // Additional logic for saving a tax

        // Retrieve the Terrain associated with the TaxeTNB
        TaxeTNB taxeTNB = taxeRequest.getTaxeTNB();
        // Check if the Terrain is not null
        try{
            // Validate the existence of the Terrain
            validateTerrainExistence(taxeRequest.getTerrainID());

            // Set the retrieved Terrain to the TaxeTNB
            taxeTNB.setTerrain(terrainRepository.findById(taxeRequest.getTerrainID()).get());

            // Save the TaxeTNB entity
            taxeTNBRepository.save(taxeTNB);
            return "tax added";
        } catch (Exception e) {
            // Handle the case where the Terrain is null (e.g., log an error or throw an exception)
            e.printStackTrace();
            return e.getMessage();
        }
    }//TODO : there still a problem in this methode see how to fix it !!!

    public String updateTax(Long id, TaxeRequest taxeRequest){
        TaxeTNB taxe = taxeTNBRepository.findById(id).get();
        if (taxe != null){
            try{
            TaxeTNB newTaxe = taxeRequest.getTaxeTNB();
            taxe.setSurface(newTaxe.getSurface());
            taxe.setAnnee(newTaxe.getAnnee());
            taxe.setMontantPaye(newTaxe.getMontantPaye());
            taxe.setTauxTerrain(newTaxe.getTauxTerrain());
            validateTerrainExistence(taxeRequest.getTerrainID());
            taxe.setTerrain(terrainRepository.findById(taxeRequest.getTerrainID()).get());
            taxeTNBRepository.save(taxe);
            return "tax updated";
            }catch (Exception e){
                e.printStackTrace();
                return  "terrain does nor exist";
            }
        }else {
            throw new IllegalArgumentException("tax does not exist");
        }
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


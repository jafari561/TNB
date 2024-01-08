package com.yazoo.service;

import com.yazoo.dto.CategorieEvent;
import com.yazoo.dto.RedevableEvent;
import com.yazoo.dto.TerrainRequest;
import com.yazoo.errors.EntityNotFoundException;
import com.yazoo.model.CategorieTerrain;
import com.yazoo.model.Redevable;
import com.yazoo.model.TaxeTNB;
import com.yazoo.model.Terrain;
import com.yazoo.repository.CategorieTerrainRepository;
import com.yazoo.repository.RedevableRepository;
import com.yazoo.repository.TaxeTNBRepository;
import com.yazoo.repository.TerrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class TerrainService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private TaxeTNBService taxeTNBService;

    private final TerrainRepository terrainRepository;
    private final CategorieTerrainRepository categorieTerrainRepository;
    private final RedevableRepository redevableRepository;
    private final TaxeTNBRepository taxeTNBRepository;

    @Autowired
    public TerrainService(TerrainRepository terrainRepository, CategorieTerrainRepository categorieTerrainRepository, RedevableRepository redevableRepository, TaxeTNBRepository taxeTNBRepository) {
        this.terrainRepository = terrainRepository;
        this.categorieTerrainRepository = categorieTerrainRepository;
        this.redevableRepository = redevableRepository;
        this.taxeTNBRepository = taxeTNBRepository;
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
                Terrain savedT = terrainRepository.save(terrain);
                savedT.setTaxesTNB(taxeTNBRepository.findByTerrainTerrainID(savedT.getTerrainID()));
                terrainRepository.save(savedT);
                return "terrain added";
            } catch (Exception e) {
                // Handle exceptions, log, or perform other error handling
                e.printStackTrace();
                return e.getMessage();
            }
        }
    }

    public String updateTerrain(Long terrainID, TerrainRequest terrainRequest){
        Terrain terrain = terrainRepository.findById(terrainID).get();
        if(terrain != null){
            if (terrain.getSurface() <= 0) {
                return "surface can not be negative ";}
            else {
            Terrain newTerrain = terrainRequest.getTerrain();
            Redevable redevable = restTemplate.getForObject("http://REDEVABLE-SERVICE/redevable/" + newTerrain.getProprietaire().getCin(), Redevable.class);
            CategorieTerrain categorie = restTemplate.getForObject("http://CATEGORIE-SERVICE/categorie/nom/" + newTerrain.getCategorie().getNomCategorie(), CategorieTerrain.class);
            terrain.setSurface(newTerrain.getSurface());
            terrain.setTaxesTNB(newTerrain.getTaxesTNB());
            terrain.setCategorie(categorie);
            terrain.setProprietaire(redevable);
            terrainRepository.save(terrain);
            return "terrain updated";}
        }else {
            throw new IllegalArgumentException("terrain does not exist");
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

    public List<Terrain> findTerrainsOfRedevable(String cin){

        Redevable redevable = redevableRepository.findByCin(cin);
        return terrainRepository.findTerrainByProprietaire(redevable);
    }

    // Additional methods for CRUD operations and other functionalities




    //processing kafka flow
    @KafkaListener(topics = "categorie-event-topic",groupId = "categorie-event-group1")
    public void processCategorieEvents(CategorieEvent categorieEvent){
        CategorieTerrain categorie = categorieEvent.getCategorie();
         if (categorieEvent.getEventType().equals("CreateCategorie")) {
        categorieTerrainRepository.save(categorie);
        }if(categorieEvent.getEventType().equals("UpdateCategorie")){
            CategorieTerrain newCategorie = categorieTerrainRepository.findById(categorie.getId()).get();
            newCategorie.setNomCategorie(categorie.getNomCategorie());
            newCategorie.setTaux(categorie.getTaux());
            categorieTerrainRepository.save(newCategorie);
        }
    }

    @KafkaListener(topics = "redevable-event-topic",groupId = "redevable-event-group1")
    public void processRedevableEvents(RedevableEvent redevableEvent){
        Redevable redevable = redevableEvent.getRedevable();
        if (redevableEvent.getEventType().equals("CreateRedevable")) {
            redevableRepository.save(redevable);
        }if(redevableEvent.getEventType().equals("UpdateRedevable")){
            Redevable newRedevable =  redevableRepository.findByCin(redevable.getCin());
            newRedevable.setNom(redevable.getNom());
            newRedevable.setPrenom(redevable.getPrenom());
            newRedevable.setAdresse(redevable.getAdresse());
            newRedevable.setTerrains(redevable.getTerrains());
            redevableRepository.save(newRedevable);
        }
    }

}


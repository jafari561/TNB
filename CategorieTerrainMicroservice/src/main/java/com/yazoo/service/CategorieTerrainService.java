package com.yazoo.service;


import com.yazoo.dto.CategorieEvent;
import com.yazoo.dto.RedevableEvent;
import com.yazoo.dto.TerrainEvent;
import com.yazoo.model.CategorieTerrain;
import com.yazoo.model.Redevable;
import com.yazoo.model.Terrain;
import com.yazoo.repository.CategorieTerrainRepository;
import com.yazoo.repository.RedevableRepository;
import com.yazoo.repository.TerrainRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieTerrainService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private final CategorieTerrainRepository categorieTerrainRepository;
    private final RedevableRepository redevableRepository;
    private final TerrainRepository terrainRepository;

    @Autowired
    public CategorieTerrainService(CategorieTerrainRepository categorieTerrainRepository, RedevableRepository redevableRepository, TerrainRepository terrainRepository) {
        this.categorieTerrainRepository = categorieTerrainRepository;
        this.redevableRepository = redevableRepository;
        this.terrainRepository = terrainRepository;
    }

    //==============================================================================================
    public List<CategorieTerrain> getAllCategories() {
        return categorieTerrainRepository.findAll();
    }

    public String saveCategory(CategorieEvent categorieEvent) {
        // Additional logic for saving a category
        CategorieTerrain categorieTerrain = categorieTerrainRepository.findByNomCategorie(categorieEvent.getCategorie().getNomCategorie());
        if(categorieTerrain==null){
            CategorieTerrain categorie = categorieTerrainRepository.save(categorieEvent.getCategorie());
            CategorieEvent event = new CategorieEvent("CreateCategorie",categorie);
            kafkaTemplate.send("categorie-event-topic",event);
            return "categorie added";
        }
        else return "categorie with name "+categorieTerrain.getNomCategorie()+" already exists";
    }

    public String updateCategory(String nomCategorie, CategorieEvent categorieEvent){

        CategorieTerrain categorieTerrain = categorieTerrainRepository.findByNomCategorie(nomCategorie);
        if(categorieTerrain!=null){
            CategorieTerrain newCategorie = categorieEvent.getCategorie();
            categorieTerrain.setNomCategorie(newCategorie.getNomCategorie());
            categorieTerrain.setTaux(newCategorie.getTaux());
            categorieTerrainRepository.save(categorieTerrain);
            CategorieEvent event = new CategorieEvent("UpdateCategorie",categorieTerrain);
            kafkaTemplate.send("categorie-event-topic",event);
            return "categorie updated";
        }else {
            throw new IllegalArgumentException("categorie does not exist");
        }
    }

    public CategorieTerrain getCategoryById(Long id) {
        return categorieTerrainRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category with ID " + id + " not found."));
    }

    public CategorieTerrain findByNomCategorie(String nomCategorie) {
        return categorieTerrainRepository.findByNomCategorie(nomCategorie);
    }
    //==============================================================================================



    // Additional methods for CRUD operations and retrieving tax rate by category



    //handling kafka flow
    @KafkaListener(topics = "redevable-event-topic",groupId = "redevable-event-group3")
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

    @KafkaListener(topics = "terrain-event-topic",groupId = "terrain-event-group3")
    public void processTerrainEvents(TerrainEvent terrainEvent){
        Terrain terrain = terrainEvent.getTerrain();
        if (terrainEvent.getEventType().equals("CreateTerrain")) {
            terrainRepository.save(terrain);
        }//TODO : the update methode !!!!
    }
}


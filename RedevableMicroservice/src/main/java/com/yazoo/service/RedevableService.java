package com.yazoo.service;


import com.yazoo.dto.CategorieEvent;
import com.yazoo.dto.RedevableEvent;
import com.yazoo.model.CategorieTerrain;
import com.yazoo.model.Redevable;
import com.yazoo.model.Terrain;
import com.yazoo.repository.CategorieTerrainRepository;
import com.yazoo.repository.RedevableRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class RedevableService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private final RedevableRepository redevableRepository;
    private final CategorieTerrainRepository categorieTerrainRepository;

    @Autowired
    public RedevableService(RedevableRepository redevableRepository, CategorieTerrainRepository categorieTerrainRepository) {
        this.redevableRepository = redevableRepository;
        this.categorieTerrainRepository = categorieTerrainRepository;
    }

    //===================================================================
    public List<Terrain> findTerrainsByCIN(String cin) {
        Redevable redevable = redevableRepository.findById(cin)
                .orElseThrow(() -> new EntityNotFoundException("Redevable with CIN " + cin + " not found."));
        return redevable.getTerrains();
    }

    public Redevable saveRedevable(RedevableEvent redevableEvent) {
        Redevable redevable = redevableEvent.getRedevable();
        validateRedevable(redevable);
        RedevableEvent event = new RedevableEvent("CreateRedevable",redevable);
        kafkaTemplate.send("redevable-event-topic", event);
        return redevableRepository.save(redevable);
    }

    public Redevable updateRedevable(String cin,RedevableEvent redevableEvent){
        Redevable redevable = redevableRepository.findById(cin).get();
        if(redevable!=null){
            Redevable newRedevable = redevableEvent.getRedevable();
            redevable.setNom(newRedevable.getNom());
            redevable.setPrenom(newRedevable.getPrenom());
            redevable.setAdresse(newRedevable.getAdresse());
            redevable.setTerrains(newRedevable.getTerrains());
            RedevableEvent event = new RedevableEvent("UpdateRedevable",redevable);
            kafkaTemplate.send("redevable-event-topic", event);
            return redevableRepository.save(redevable);
        }else {
            throw new IllegalArgumentException("redevable does not exist");
        }

    }

    public List<Redevable> getAllRedevables() {
        return redevableRepository.findAll();
    }

    public Redevable getRedevableByCIN(String cin) {
        return redevableRepository.findById(cin)
                .orElseThrow(() -> new IllegalStateException("Redevable with CIN " + cin + " not found."));
    }

    private void validateRedevable(Redevable redevable) {
        if (!Pattern.matches("[A-Z]{2}\\d{5,6}", redevable.getCin())) {
            throw new IllegalArgumentException("Invalid CIN format. It should be two letters followed by 5 or 6 numbers.");
        }

        if (!Pattern.matches("[a-zA-Z]+", redevable.getNom())) {
            throw new IllegalArgumentException("Invalid 'nom' format. It should contain only letters.");
        }

        if (!Pattern.matches("[a-zA-Z]+", redevable.getPrenom())) {
            throw new IllegalArgumentException("Invalid 'prenom' format. It should contain only letters.");
        }
    }
    //===================================================================


    //processing kafka flow
    @KafkaListener(topics = "categorie-event-topic",groupId = "categorie-event-group")
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


    // Additional methods for CRUD operations and historical taxes
}


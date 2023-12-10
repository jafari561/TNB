package com.yazoo.redevablemicroservice.service;

import com.yazoo.redevablemicroservice.model.Redevable;
import com.yazoo.redevablemicroservice.model.Terrain;
import com.yazoo.redevablemicroservice.repository.RedevableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class RedevableService {

    private final RedevableRepository redevableRepository;
    @Autowired
    public RedevableService(RedevableRepository redevableRepository) {
        this.redevableRepository = redevableRepository;
    }


    public List<Terrain> findTerrainsByCIN(String cin) {
        Redevable redevable = redevableRepository.findById(cin)
                .orElseThrow(() -> new IllegalArgumentException("Redevable with CIN " + cin + " not found."));

        return redevable.getTerrains();
    }


    public Redevable saveRedevable(Redevable redevable) {
        // Validate CIN format (two letters followed by 5 or 6 numbers)
        if (!Pattern.matches("[A-Z]{2}\\d{5,6}", redevable.getCin())) {
            throw new IllegalArgumentException("Invalid CIN format.");
        }

        // Validate "nom" and "prenom" contain only letters
        if (!Pattern.matches("[a-zA-Z]+", redevable.getNom())) {
            throw new IllegalArgumentException("Invalid 'nom' format.");
        }
        if (!Pattern.matches("[a-zA-Z]+", redevable.getPrenom())) {
            throw new IllegalArgumentException("Invalid 'prenom' format.");
        }
        //save the Redevable
        return redevableRepository.save(redevable);
    }

    public List<Redevable> getAllRedevables() {
        return redevableRepository.findAll();
    }

    public Redevable getRedevableByCIN(String cin) {
        Redevable redevable = redevableRepository.findById(cin)
                .orElseThrow(() -> new IllegalArgumentException("Redevable with CIN " + cin + " not found."));

        return redevable;
    }


}

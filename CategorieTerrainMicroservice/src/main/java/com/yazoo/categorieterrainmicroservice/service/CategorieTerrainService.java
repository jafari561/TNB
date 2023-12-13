package com.yazoo.categorieterrainmicroservice.service;

import com.yazoo.categorieterrainmicroservice.model.CategorieTerrain;
import com.yazoo.categorieterrainmicroservice.repository.CategorieTerrainRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieTerrainService {
    private final CategorieTerrainRepository categorieTerrainRepository;

    @Autowired
    public CategorieTerrainService(CategorieTerrainRepository categorieTerrainRepository) {
        this.categorieTerrainRepository = categorieTerrainRepository;
    }

    //==============================================================================================
    public List<CategorieTerrain> getAllCategories() {
        return categorieTerrainRepository.findAll();
    }

    public String saveCategory(CategorieTerrain categorieTerrain) {
        // Additional logic for saving a category
        CategorieTerrain C = categorieTerrainRepository.findByNomCategorie(categorieTerrain.getNomCategorie());
        if(C==null){categorieTerrainRepository.save(categorieTerrain);return "categorie added";}
        else return "categorie with name "+C.getNomCategorie()+" already exists";
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
}


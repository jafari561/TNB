package com.yazoo.categorieterrainmicroservice.service;

import com.yazoo.categorieterrainmicroservice.model.CategorieTerrain;
import com.yazoo.categorieterrainmicroservice.repository.CategorieTerrainRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategorieTerrainService {
    private final CategorieTerrainRepository categorieTerrainRepository;

    @Autowired
    public CategorieTerrainService(CategorieTerrainRepository categorieTerrainRepository) {
        this.categorieTerrainRepository = categorieTerrainRepository;
    }

    public List<CategorieTerrain> getAllCategories() {
        return categorieTerrainRepository.findAll();
    }

    public CategorieTerrain saveCategory(CategorieTerrain categorieTerrain) {
        // Additional logic for saving a category
        return categorieTerrainRepository.save(categorieTerrain);
    }

    public CategorieTerrain getCategoryById(Long id) {
        Optional<CategorieTerrain> optionalCategory = categorieTerrainRepository.findById(id);
        return optionalCategory.orElseThrow(() -> new EntityNotFoundException("Category with ID " + id + " not found."));
    }

    public CategorieTerrain getCategoryByNom(String nomCategorie) {
        Optional<CategorieTerrain> category = Optional.ofNullable(categorieTerrainRepository.findByNomCategorie(nomCategorie));
        return category.orElseThrow(() -> new EntityNotFoundException("Category with name " + nomCategorie + " not found."));
    }


}

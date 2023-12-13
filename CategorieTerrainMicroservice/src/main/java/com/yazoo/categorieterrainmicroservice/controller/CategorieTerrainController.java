package com.yazoo.categorieterrainmicroservice.controller;

import com.yazoo.categorieterrainmicroservice.model.CategorieTerrain;
import com.yazoo.categorieterrainmicroservice.service.CategorieTerrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorie")
public class CategorieTerrainController {
    private final CategorieTerrainService categorieTerrainService;

    @Autowired
    public CategorieTerrainController(CategorieTerrainService categorieTerrainService) {
        this.categorieTerrainService = categorieTerrainService;
    }

    //======================================================================================
    @GetMapping("/all")
    public List<CategorieTerrain> getAllCategories() {
        return categorieTerrainService.getAllCategories();
    }

    //======================================================================================
    @PostMapping("/save")
    public String saveCategory(@RequestBody CategorieTerrain categorieTerrain) {
        return categorieTerrainService.saveCategory(categorieTerrain);
    }

    //======================================================================================
    @GetMapping("/{id}")
    public CategorieTerrain getCategoryById(@PathVariable Long id) {
        return categorieTerrainService.getCategoryById(id);
    }

    //======================================================================================
    @GetMapping("/nom/{nomCategorie}")
    public CategorieTerrain findByNomCategorie(@PathVariable String nomCategorie) {
        return categorieTerrainService.findByNomCategorie(nomCategorie);
    }


    // Additional endpoints for CRUD operations and retrieving tax rate by category
}


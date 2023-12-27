package com.yazoo.controller;

import com.yazoo.dto.CategorieEvent;
import com.yazoo.model.CategorieTerrain;
import com.yazoo.service.CategorieTerrainService;
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
    public String saveCategory(@RequestBody CategorieEvent categorieEvent) {
        return categorieTerrainService.saveCategory(categorieEvent);
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

    @PutMapping("/{nomCategorie}")
    public String updateCategorie(@PathVariable String nomCategorie,@RequestBody CategorieEvent categorieEvent){
        return categorieTerrainService.updateCategory(nomCategorie,categorieEvent);
    }


    // Additional endpoints for CRUD operations and retrieving tax rate by category
}


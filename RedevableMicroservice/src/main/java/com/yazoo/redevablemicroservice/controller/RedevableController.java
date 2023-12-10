package com.yazoo.redevablemicroservice.controller;


import com.yazoo.redevablemicroservice.model.Redevable;
import com.yazoo.redevablemicroservice.model.Terrain;
import com.yazoo.redevablemicroservice.service.RedevableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/redevable")
public class RedevableController {

    @Autowired
    private RedevableService redevableService;

    @GetMapping("/{cin}/terrains")
    public List<Terrain> getTerrainsByCIN(@PathVariable String cin) {
        return redevableService.findTerrainsByCIN(cin);
    }

    @PostMapping("/save")
    public Redevable saveRedevable(@RequestBody Redevable redevable) {
        return redevableService.saveRedevable(redevable);
    }

    @GetMapping("/all")
    public List<Redevable> getAllRedevables() {
        return redevableService.getAllRedevables();
    }

    @GetMapping("/{cin}")
    public Redevable getRedevableByCIN(@PathVariable String cin) {
        return redevableService.getRedevableByCIN(cin);
    }

}



package com.yazoo.controller;

import com.yazoo.dto.RedevableEvent;
import com.yazoo.model.Redevable;
import com.yazoo.model.Terrain;
import com.yazoo.service.RedevableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/redevable")
@CrossOrigin
public class RedevableController {
    private final RedevableService redevableService;

    @Autowired
    public RedevableController(RedevableService redevableService) {
        this.redevableService = redevableService;
    }

    //===================================================================
    @GetMapping("/{cin}/terrains")
    public List<Terrain> findTerrainsByCIN(@PathVariable String cin) {
        return redevableService.findTerrainsByCIN(cin);
    }

    //===================================================================
    @GetMapping("/{cin}")
    public Redevable findByCIN(@PathVariable String cin) {
        return redevableService.getRedevableByCIN(cin);
    }

    //===================================================================
    @GetMapping("/all")
    public List<Redevable> findAll() {
        return redevableService.getAllRedevables();
    }


    //===================================================================
    @PostMapping("/save")
    public Redevable saveRedevable(@RequestBody RedevableEvent event) {
        return redevableService.saveRedevable(event);
    }

    @PutMapping("/{cin}")
    public Redevable updateRedevable(@PathVariable String cin,@RequestBody RedevableEvent redevableEvent ){
        return redevableService.updateRedevable(cin,redevableEvent);
    }

    // Additional endpoints for CRUD operations and other functionalities
}


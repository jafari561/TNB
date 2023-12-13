package com.yazoo.redevablemicroservice.controller;

import com.yazoo.redevablemicroservice.dto.RedevableRequest;
import com.yazoo.redevablemicroservice.model.Redevable;
import com.yazoo.redevablemicroservice.model.Terrain;
import com.yazoo.redevablemicroservice.service.RedevableService;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/redevable")
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
    public Redevable saveRedevable(@RequestBody RedevableRequest request) {
        Redevable re = request.getRedevable();
        return redevableService.saveRedevable(re);
    }

    // Additional endpoints for CRUD operations and other functionalities
}


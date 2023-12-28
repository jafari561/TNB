package com.yazoo.controller;


import com.yazoo.dto.TaxeRequest;
import com.yazoo.model.TaxeTNB;
import com.yazoo.service.TaxeTNBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/taxe")
public class TaxeTNBController {
    private final TaxeTNBService taxeTNBService;

    @Autowired
    public TaxeTNBController(TaxeTNBService taxeTNBService) {
        this.taxeTNBService = taxeTNBService;

    }

    @GetMapping("/all")
    public List<TaxeTNB> getAllTaxes() {
        return taxeTNBService.getAllTaxes();
    }

    @PostMapping("/save")
    public String saveTax(@RequestBody TaxeRequest request) {
        return taxeTNBService.saveTax(request);
    }

    @PutMapping("{id}")
    public String updateTax(@PathVariable Long id, @RequestBody TaxeRequest taxeRequest){
        return taxeTNBService.updateTax(id,taxeRequest);
    }

    @GetMapping("/{terrainId}/history")
    public List<TaxeTNB> getHistoricalTaxesForTerrain(@PathVariable Long terrainId) {
        return taxeTNBService.getHistoricalTaxesForTerrain(terrainId);
    }


    // Additional endpoints for CRUD operations and other functionalities
}


package com.yazoo.terrainmicroservice.dto;


import com.yazoo.terrainmicroservice.model.TaxeTNB;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxeRequest {

    private TaxeTNB taxeTNB;
}

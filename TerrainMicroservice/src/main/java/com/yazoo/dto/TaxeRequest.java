package com.yazoo.dto;


import com.yazoo.model.TaxeTNB;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxeRequest {

    private TaxeTNB taxeTNB;
    private Long terrainID;
}

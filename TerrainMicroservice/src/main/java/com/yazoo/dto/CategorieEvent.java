package com.yazoo.dto;


import com.yazoo.model.CategorieTerrain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategorieEvent {

    private String eventType;
    private CategorieTerrain categorie;
}

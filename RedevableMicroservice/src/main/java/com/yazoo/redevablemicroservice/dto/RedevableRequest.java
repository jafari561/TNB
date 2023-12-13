package com.yazoo.redevablemicroservice.dto;

import com.yazoo.redevablemicroservice.model.Redevable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedevableRequest {

    private Redevable redevable;
}

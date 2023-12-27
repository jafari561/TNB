package com.yazoo.dto;

import com.yazoo.model.Redevable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedevableEvent {

    private String eventType;
    private Redevable redevable;
}

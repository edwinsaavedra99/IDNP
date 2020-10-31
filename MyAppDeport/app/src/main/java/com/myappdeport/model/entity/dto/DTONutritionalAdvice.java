package com.myappdeport.model.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTONutritionalAdvice {
    private String title;
    private String shortDescription;
    private String longDescription;
    /**
     * Archivo en cloud storage.
     */
    private String imageUrlCloudStorage;
}

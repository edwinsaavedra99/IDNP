package com.myappdeport.model.entity.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DTONutritionalAdvice extends DTOEntity {
    private String title;
    private String shortDescription;
    private String longDescription;
    /**
     * Archivo en cloud storage.
     */
    private String imageUrlCloudStorage;
}

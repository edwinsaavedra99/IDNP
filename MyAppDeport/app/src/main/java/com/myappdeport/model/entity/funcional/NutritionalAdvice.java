package com.myappdeport.model.entity.funcional;

import java.io.File;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NutritionalAdvice {
    private String documentId;
    private String title;
    private String shortDescription;
    private String longDescription;
    /**
     * Dirección del archivo.
     */
    private String imageUrlCloudStorage;
    /**
     * Archivo en cloud storage.
     */
    private File image;
}

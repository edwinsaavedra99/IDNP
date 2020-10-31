package com.myappdeport.model.entity.funcional;

import java.io.File;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Song {
    private Long id;
    private String name;
    private String author;
    private LocalTime duration;
    /**
     * Ruta del archivo de la canción.
     */
    private String songRoute;
    /**
     * Archivo de la canción.
     */
    private File songFile;
}

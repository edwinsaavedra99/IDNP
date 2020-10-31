package com.myappdeport.model.entity.database;

import com.google.firebase.firestore.Exclude;

import java.io.File;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ENutritionalAdvice extends EntityDatabase {
    private String title;
    private String shortDescription;
    private String longDescription;
    /**
     * Ver como se haria una implemetación con cloud storage.
     */
    private String imageUrlCloudStorage;
    /**
     * Archivo en cloud storage.
     */
    private File image;

    /**
     * Firebase constructor
     *
     * @param documentId           Es el identificador del documento de firebase.
     * @param title                Es el titulo del aviso nutricional.
     * @param shortDescription     Es la descripción breve.
     * @param longDescription      Es la descripción a detalle.
     * @param imageUrlCloudStorage Es la url del archivo que se almaceno en cloud storage.
     * @param image                Es el archivo que se guardara en el cloud storage.
     */
    public ENutritionalAdvice(String documentId, String title, String shortDescription, String longDescription, String imageUrlCloudStorage, File image) {
        super(null, documentId);
        this.title = title;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.imageUrlCloudStorage = imageUrlCloudStorage;
        this.image = image;
    }

    @Exclude
    public File getImage() {
        return image;
    }
}

package com.myappdeport.model.entity.database;

import lombok.*;


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
     * Firebase constructor
     *
     * @param documentId           Es el identificador del documento de firebase.
     * @param title                Es el titulo del aviso nutricional.
     * @param shortDescription     Es la descripción breve.
     * @param longDescription      Es la descripción a detalle.
     * @param imageUrlCloudStorage Es la url del archivo que se almaceno en cloud storage.
     */
    public ENutritionalAdvice(String documentId, String title, String shortDescription, String longDescription, String imageUrlCloudStorage) {
        super(null, documentId);
        this.title = title;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.imageUrlCloudStorage = imageUrlCloudStorage;
    }
}

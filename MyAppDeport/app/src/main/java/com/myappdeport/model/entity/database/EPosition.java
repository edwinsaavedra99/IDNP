package com.myappdeport.model.entity.database;

import androidx.room.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
public class EPosition extends EntityDatabase {
    private Double latitude;
    private Double longitude;
    private Double distance;

    /**
     * Firebase and SQLite constructor
     *
     * @param id         Identificador en SQLite
     * @param documentId Identificador en Firebase
     * @param latitude   Latitud
     * @param longitude  Longitud
     * @param distance   Distancia
     */
    public EPosition(Long id, String documentId, Double latitude, Double longitude, Double distance) {
        super(id, documentId);
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }

    /**
     * SQLite constructor
     *
     * @param id        Identificador en SQLite
     * @param latitude  Latitud
     * @param longitude Longitud
     * @param distance  Distancia
     */
    public EPosition(Long id, Double latitude, Double longitude, Double distance) {
        this(id, null, latitude, longitude, distance);
    }

    /**
     * Firebase constructor
     *
     * @param documentId Identificador en Firebase
     * @param latitude   Latitud
     * @param longitude  Longitud
     * @param distance   Distancia
     */
    public EPosition(String documentId, Double latitude, Double longitude, Double distance) {
        this(null, documentId, latitude, longitude, distance);
    }


}

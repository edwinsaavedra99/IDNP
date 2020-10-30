package com.myappdeport.model.entity.database;

import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.List;

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
@Entity
public class ERoute extends EntityDatabase {
    private Double totalDistance;
    private Double rhythm;
    /**
     * List of point documentId on firebase.
     */
    @Ignore
    private List<String> positionDocumentIds;

    public ERoute(Long id, String documentId, Double totalDistance, Double rhythm, List<String> positionDocumentIds) {
        super(id, documentId);
        this.totalDistance = totalDistance;
        this.rhythm = rhythm;
        this.positionDocumentIds = positionDocumentIds;
    }

    /**
     * SQLite constructor
     *
     * @param id            Identificador en SQLite
     * @param totalDistance Es la distancia total recorrida.
     * @param rhythm        Es el ritmo que se siguio en la ruta
     */
    public ERoute(Long id, Double totalDistance, Double rhythm) {
        this(id, null, totalDistance, rhythm, null);
    }

    /**
     * Firebase constructor
     *
     * @param documentId          Identificador en Firebase
     * @param totalDistance       Es la distancia total recorrida.
     * @param rhythm              Es el ritmo que se siguio en la ruta
     * @param positionDocumentIds Son los idde los documentos en firebase
     */
    public ERoute(String documentId, Double totalDistance, Double rhythm, List<String> positionDocumentIds) {
        this(null, documentId, totalDistance, rhythm, positionDocumentIds);
    }
}

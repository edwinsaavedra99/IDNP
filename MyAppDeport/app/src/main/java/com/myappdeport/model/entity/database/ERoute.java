package com.myappdeport.model.entity.database;

import androidx.room.Entity;
import androidx.room.Ignore;

import com.google.firebase.firestore.Exclude;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
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
    /**
     * Sin referencia a sqlite y a firebase
     */
    @Ignore
    private List<EPosition> positions;

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


    public ERoute() {

    }

    public Double getTotalDistance() {
        return this.totalDistance;
    }

    public Double getRhythm() {
        return this.rhythm;
    }

    public List<String> getPositionDocumentIds() {
        return this.positionDocumentIds;
    }

    public void setTotalDistance(Double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public void setRhythm(Double rhythm) {
        this.rhythm = rhythm;
    }

    public void setPositionDocumentIds(List<String> positionDocumentIds) {
        this.positionDocumentIds = positionDocumentIds;
    }

    @Exclude
    public List<EPosition> getPositions() {
        return this.positions;
    }

    public void setPositions(List<EPosition> positions) {
        this.positions = positions;
    }
}

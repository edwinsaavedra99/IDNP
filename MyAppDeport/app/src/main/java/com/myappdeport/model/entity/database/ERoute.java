package com.myappdeport.model.entity.database;

import androidx.room.Entity;
import androidx.room.Ignore;

import com.google.firebase.firestore.Exclude;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
public class ERoute extends EntityDatabase {
    private Double totalDistance;
    private Double rhythm;
    /**
     * Sin referencia a sqlite y a firebase
     */
    @Ignore
    private List<EPosition> positions;

    @Ignore
    public ERoute(Long id, String documentId, Double totalDistance, Double rhythm) {
        super(id, documentId);
        this.totalDistance = totalDistance;
        this.rhythm = rhythm;
    }

    /**
     * SQLite constructor
     *
     * @param id            Identificador en SQLite
     * @param totalDistance Es la distancia total recorrida.
     * @param rhythm        Es el ritmo que se siguio en la ruta
     */
    @Ignore
    public ERoute(Long id, Double totalDistance, Double rhythm) {
        this(id, null, totalDistance, rhythm);
    }

    /**
     * Firebase constructor
     *
     * @param documentId    Identificador en Firebase
     * @param totalDistance Es la distancia total recorrida.
     * @param rhythm        Es el ritmo que se siguio en la ruta
     */
    @Ignore
    public ERoute(String documentId, Double totalDistance, Double rhythm) {
        this(null, documentId, totalDistance, rhythm);
    }

    @Ignore
    public ERoute(Double totalDistance, Double rhythm, List<EPosition> positions) {
        this.totalDistance = totalDistance;
        this.rhythm = rhythm;
        this.positions = positions;
    }

    public ERoute() {

    }

    public Double getTotalDistance() {
        return this.totalDistance;
    }

    public Double getRhythm() {
        return this.rhythm;
    }

    public void setTotalDistance(Double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public void setRhythm(Double rhythm) {
        this.rhythm = rhythm;
    }

    @Exclude
    public List<EPosition> getPositions() {
        return this.positions;
    }

    public void setPositions(List<EPosition> positions) {
        this.positions = positions;
    }
}

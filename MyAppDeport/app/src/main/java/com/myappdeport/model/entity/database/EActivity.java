package com.myappdeport.model.entity.database;

import androidx.room.Entity;
import androidx.room.Ignore;

import com.google.firebase.firestore.Exclude;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public class EActivity extends EntityDatabase {
    private String startTime;
    private String endTime;
    private Double kiloCalories;
    /**
     * Id del documento route firebase.
     */
    @Ignore
    private String routeDocumentId;
    @Ignore
    private ERoute eRoute;

    public EActivity(Long id, String documentId, String startTime, String endTime, Double kiloCalories, String routeDocumentId) {
        super(id, documentId);
        this.startTime = startTime;
        this.endTime = endTime;
        this.kiloCalories = kiloCalories;
        this.routeDocumentId = routeDocumentId;
    }

    /**
     * SQLite constructor
     *
     * @param id           Identificador en SQLite
     * @param startTime    Es la hora de inicio
     * @param endTime      Es la hora de finalización.
     * @param kiloCalories Son las kilocalorias invertidas.
     */
    public EActivity(Long id, String startTime, String endTime, Double kiloCalories) {
        this(id, null, startTime, endTime, kiloCalories, null);
    }

    /**
     * Firebase constructor
     *
     * @param documentId      Identificador en SQLite
     * @param startTime       Es la hora de inicio
     * @param endTime         Es la hora de finalización.
     * @param kiloCalories    Son las kilocalorias invertidas.
     * @param routeDocumentId Es el id del documento de ruta.
     */
    public EActivity(String documentId, String startTime, String endTime, Double kiloCalories, String routeDocumentId) {
        this(null, documentId, startTime, endTime, kiloCalories, routeDocumentId);
    }

    public String getStartTime() {
        return this.startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public Double getKiloCalories() {
        return this.kiloCalories;
    }

    public String getRouteDocumentId() {
        return this.routeDocumentId;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setKiloCalories(Double kiloCalories) {
        this.kiloCalories = kiloCalories;
    }

    public void setRouteDocumentId(String routeDocumentId) {
        this.routeDocumentId = routeDocumentId;
    }

    @Exclude
    public ERoute getERoute() {
        return this.eRoute;
    }

    public void setERoute(ERoute eRoute) {
        this.eRoute = eRoute;
    }
}


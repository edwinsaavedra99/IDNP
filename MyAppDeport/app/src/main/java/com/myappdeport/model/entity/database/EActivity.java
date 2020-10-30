package com.myappdeport.model.entity.database;

import androidx.room.Entity;
import androidx.room.Ignore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public class EActivity extends EntityDatabase {
    private String startTime;
    private String endTime;
    private Integer kiloCalories;
    /**
     * Id del documento route firebase.
     */
    @Ignore
    private String routeDocumentId;

    public EActivity(Long id, String documentId, String startTime, String endTime, Integer kiloCalories, String routeDocumentId) {
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
    public EActivity(Long id, String startTime, String endTime, Integer kiloCalories) {
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
    public EActivity(String documentId, String startTime, String endTime, Integer kiloCalories, String routeDocumentId) {
        this(null, documentId, startTime, endTime, kiloCalories, routeDocumentId);
    }
}


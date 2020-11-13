package com.myappdeport.model.entity.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

import com.google.firebase.firestore.Exclude;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity(foreignKeys = {
        @ForeignKey(
                entity = ERoute.class,
                parentColumns = "id",
                childColumns = "idERoute",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = EUser.class,
                parentColumns = "id",
                childColumns = "idEUser",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        )
}, indices = {
        @Index("idERoute"),
        @Index("idEUser")
}
)
public class EActivity extends EntityDatabase {
    private String startTime;
    private String endTime;
    private Double kiloCalories;
    /**
     * Id del documento route firebase.
     */
    @Ignore
    private String routeDocumentId;
    /**
     * Id del documento user firebase.
     */
    @Ignore
    private String userDocumentId;
    /**
     * Id de la ruta SQLite.
     */
    private Long idERoute;
    /**
     * Id del usuario SQLite.
     */
    private Long idEUser;
    @Ignore
    private ERoute eRoute;

    @Ignore
    public EActivity(Long id, String documentId, String startTime, String endTime, Double kiloCalories, String routeDocumentId, Long idERoute) {
        super(id, documentId);
        this.startTime = startTime;
        this.endTime = endTime;
        this.kiloCalories = kiloCalories;
        this.routeDocumentId = routeDocumentId;
        this.idERoute = idERoute;
    }

    /**
     * SQLite constructor
     *
     * @param id           Identificador en SQLite
     * @param startTime    Es la hora de inicio
     * @param endTime      Es la hora de finalización.
     * @param kiloCalories Son las kilocalorias invertidas.
     */
    @Ignore
    public EActivity(Long id, String startTime, String endTime, Double kiloCalories, Long idERoute) {
        this(id, null, startTime, endTime, kiloCalories, null, idERoute);
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
    @Ignore
    public EActivity(String documentId, String startTime, String endTime, Double kiloCalories, String routeDocumentId, Long idERoute) {
        this(null, documentId, startTime, endTime, kiloCalories, routeDocumentId, idERoute);
    }

    /**
     * Firebase constructor
     *
     * @param idERoute        Identificador en SQLite
     * @param startTime       Es la hora de inicio
     * @param endTime         Es la hora de finalización.
     * @param kiloCalories    Son las kilocalorias invertidas.
     * @param routeDocumentId Es el id del documento de ruta.
     * @param eRoute          Es la ruta.
     */
    @Ignore
    public EActivity(String startTime, String endTime, Double kiloCalories, String routeDocumentId, Long idERoute, ERoute eRoute) {
        this(null, null, startTime, endTime, kiloCalories, routeDocumentId, idERoute);
    }

    @Ignore
    public EActivity(String startTime, String endTime, Double kiloCalories) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.kiloCalories = kiloCalories;
    }

    @Ignore
    public EActivity(String startTime, String endTime, Double kiloCalories, ERoute eRoute) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.kiloCalories = kiloCalories;
        this.eRoute = eRoute;
    }

    public EActivity() {
    }


    @Exclude
    public ERoute getERoute() {
        return this.eRoute;
    }

    @Exclude
    public Long getIdERoute() {
        return idERoute;
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

    public void setIdERoute(Long idERoute) {
        this.idERoute = idERoute;
    }

    public void setERoute(ERoute eRoute) {
        this.eRoute = eRoute;
    }

    public String getUserDocumentId() {
        return this.userDocumentId;
    }

    @Exclude
    public Long getIdEUser() {
        return this.idEUser;
    }

    public void setUserDocumentId(String userDocumentId) {
        this.userDocumentId = userDocumentId;
    }

    public void setIdEUser(Long idEUser) {
        this.idEUser = idEUser;
    }
}


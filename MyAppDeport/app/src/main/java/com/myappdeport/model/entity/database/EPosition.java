package com.myappdeport.model.entity.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

import com.google.firebase.firestore.Exclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity(foreignKeys = @ForeignKey(
        entity = ERoute.class,
        parentColumns = "id",
        childColumns = "idERoute",
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
),
        indices = @Index("idERoute")
)
public class EPosition extends EntityDatabase {
    private Double latitude;
    private Double longitude;
    private Double distance;

    private Long idERoute;
    @Ignore
    private String eRouteDocumentId;

    /**
     * Firebase and SQLite constructor
     *
     * @param id         Identificador en SQLite
     * @param documentId Identificador en Firebase
     * @param latitude   Latitud
     * @param longitude  Longitud
     * @param distance   Distancia
     */
    @Ignore
    public EPosition(Long id, String documentId, Double latitude, Double longitude, Double distance, Long idERoute) {
        super(id, documentId);
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
        this.idERoute = idERoute;
    }

    /**
     * SQLite constructor
     *
     * @param id        Identificador en SQLite
     * @param latitude  Latitud
     * @param longitude Longitud
     * @param distance  Distancia
     */
    @Ignore
    public EPosition(Long id, Double latitude, Double longitude, Double distance, Long idERoute) {
        this(id, null, latitude, longitude, distance, idERoute);
    }

    /**
     * Firebase constructor
     *
     * @param documentId Identificador en Firebase
     * @param latitude   Latitud
     * @param longitude  Longitud
     * @param distance   Distancia
     */
    @Ignore
    public EPosition(String documentId, Double latitude, Double longitude, Double distance, Long idERoute) {
        this(null, documentId, latitude, longitude, distance, idERoute);
    }

    @Ignore
    public EPosition(Double latitude, Double longitude, Double distance, Long idERoute) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
        this.idERoute = idERoute;
    }

    @Ignore
    public EPosition(Double latitude, Double longitude, Double distance) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }

    public EPosition() {
    }

    @Exclude
    public Long getIdERoute() {
        return idERoute;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public Double getDistance() {
        return this.distance;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public void setIdERoute(Long idERoute) {
        this.idERoute = idERoute;
    }
}

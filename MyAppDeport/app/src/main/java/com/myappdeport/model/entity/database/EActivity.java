package com.myappdeport.model.entity.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

import com.google.firebase.firestore.Exclude;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EActivity extends EntityDatabase {
    private String startTime;
    private String endTime;
    private Double kiloCalories;
    private String date;
    private String title;
    private String description;
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

    /**
     * All arguments constructors.
     *
     * @param id              Identificador en SQLite.
     * @param documentId      Identificador en Firebase.
     * @param startTime       Es el tiempo de inicio.
     * @param endTime         Es el tiempo de fin.
     * @param kiloCalories    Son las kilocalorias.
     * @param date            Es la fecha de la actividad.
     * @param title           Es el titulo de la actividad.
     * @param description     Es la descripción de la actividad.
     * @param routeDocumentId Es el id de la ruta en firebase.
     * @param userDocumentId  Es el id del usuario en firebase.
     * @param idERoute        Es el id de la ruta en SQLite.
     * @param idEUser         Es el id del usuario en SQLite.
     * @param eRoute          Es la ruta asociada.
     */
    @Ignore
    public EActivity(Long id, String documentId, String startTime, String endTime, Double kiloCalories, String date, String title, String description, String routeDocumentId, String userDocumentId, Long idERoute, Long idEUser, ERoute eRoute) {
        super(id, documentId);
        this.startTime = startTime;
        this.endTime = endTime;
        this.kiloCalories = kiloCalories;
        this.date = date;
        this.title = title;
        this.description = description;
        this.routeDocumentId = routeDocumentId;
        this.userDocumentId = userDocumentId;
        this.idERoute = idERoute;
        this.idEUser = idEUser;
        this.eRoute = eRoute;
    }

    /**
     * SQLite constructor.
     *
     * @param id           Identificador en SQLite.
     * @param startTime    Es el tiempo de inicio.
     * @param endTime      Es el tiempo de fin.
     * @param kiloCalories Son las kilocalorias.
     * @param date         Es la fecha de la actividad.
     * @param title        Es el titulo de la actividad.
     * @param description  Es la descripción de la actividad.
     * @param idERoute     Es el id de la ruta en SQLite.
     * @param idEUser      Es el id del usuario en SQLite.
     * @param eRoute       Es la ruta asociada.
     */
    @Ignore
    public EActivity(Long id, String startTime, String endTime, Double kiloCalories, String date, String title, String description, Long idERoute, Long idEUser, ERoute eRoute) {
        this(id, null, startTime, endTime, kiloCalories, date, title, description, null, null, idERoute, idEUser, eRoute);
    }

    /**
     * Firebase constructor.
     *
     * @param documentId      Identificador en Firebase.
     * @param startTime       Es el tiempo de inicio.
     * @param endTime         Es el tiempo de fin.
     * @param kiloCalories    Son las kilocalorias.
     * @param date            Es la fecha de la actividad.
     * @param title           Es el titulo de la actividad.
     * @param description     Es la descripción de la actividad.
     * @param routeDocumentId Es el id de la ruta en firebase.
     * @param userDocumentId  Es el id del usuario en firebase.
     * @param eRoute          Es la ruta asociada.
     */
    @Ignore
    public EActivity(String documentId, String startTime, String endTime, Double kiloCalories, String date, String title, String description, String routeDocumentId, String userDocumentId, ERoute eRoute) {
        this(null, documentId, startTime, endTime, kiloCalories, date, title, description, routeDocumentId, userDocumentId, null, null, eRoute);
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

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}


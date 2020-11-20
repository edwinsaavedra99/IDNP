package com.myappdeport.model.entity.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

import com.google.firebase.firestore.Exclude;

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

    @Ignore
    public EActivity(Long id, String documentId, String startTime, String endTime, String date, Double kiloCalories, String routeDocumentId, Long idERoute) {
        super(id, documentId);
        this.startTime = startTime;
        this.endTime = endTime;
        this.kiloCalories = kiloCalories;
        this.routeDocumentId = routeDocumentId;
        this.idERoute = idERoute;
        this.date = date;
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
    public EActivity(Long id, String startTime, String endTime, String date, Double kiloCalories, Long idERoute) {
        this(id, null, startTime, endTime, date, kiloCalories, null, idERoute);
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
    public EActivity(String documentId, String startTime, String endTime, String date, Double kiloCalories, String routeDocumentId) {
        this(null, documentId, startTime, endTime, date, kiloCalories, routeDocumentId, null);
    }

    @Ignore
    public EActivity(String startTime, String endTime, String date, Double kiloCalories) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.kiloCalories = kiloCalories;
    }

    @Ignore
    public EActivity(String startTime, String endTime, String date, Double kiloCalories, ERoute eRoute) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
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

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof EActivity)) return false;
        final EActivity other = (EActivity) o;
        if (!other.canEqual((Object) this)) return false;
        if (!super.equals(o)) return false;
        final Object this$startTime = this.getStartTime();
        final Object other$startTime = other.getStartTime();
        if (this$startTime == null ? other$startTime != null : !this$startTime.equals(other$startTime))
            return false;
        final Object this$endTime = this.getEndTime();
        final Object other$endTime = other.getEndTime();
        if (this$endTime == null ? other$endTime != null : !this$endTime.equals(other$endTime))
            return false;
        final Object this$kiloCalories = this.getKiloCalories();
        final Object other$kiloCalories = other.getKiloCalories();
        if (this$kiloCalories == null ? other$kiloCalories != null : !this$kiloCalories.equals(other$kiloCalories))
            return false;
        final Object this$date = this.getDate();
        final Object other$date = other.getDate();
        if (this$date == null ? other$date != null : !this$date.equals(other$date)) return false;
        final Object this$title = this.getTitle();
        final Object other$title = other.getTitle();
        if (this$title == null ? other$title != null : !this$title.equals(other$title))
            return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        final Object this$routeDocumentId = this.getRouteDocumentId();
        final Object other$routeDocumentId = other.getRouteDocumentId();
        if (this$routeDocumentId == null ? other$routeDocumentId != null : !this$routeDocumentId.equals(other$routeDocumentId))
            return false;
        final Object this$userDocumentId = this.getUserDocumentId();
        final Object other$userDocumentId = other.getUserDocumentId();
        if (this$userDocumentId == null ? other$userDocumentId != null : !this$userDocumentId.equals(other$userDocumentId))
            return false;
        final Object this$idERoute = this.getIdERoute();
        final Object other$idERoute = other.getIdERoute();
        if (this$idERoute == null ? other$idERoute != null : !this$idERoute.equals(other$idERoute))
            return false;
        final Object this$idEUser = this.getIdEUser();
        final Object other$idEUser = other.getIdEUser();
        if (this$idEUser == null ? other$idEUser != null : !this$idEUser.equals(other$idEUser))
            return false;
        final Object this$eRoute = this.getERoute();
        final Object other$eRoute = other.getERoute();
        if (this$eRoute == null ? other$eRoute != null : !this$eRoute.equals(other$eRoute))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof EActivity;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $startTime = this.getStartTime();
        result = result * PRIME + ($startTime == null ? 43 : $startTime.hashCode());
        final Object $endTime = this.getEndTime();
        result = result * PRIME + ($endTime == null ? 43 : $endTime.hashCode());
        final Object $kiloCalories = this.getKiloCalories();
        result = result * PRIME + ($kiloCalories == null ? 43 : $kiloCalories.hashCode());
        final Object $date = this.getDate();
        result = result * PRIME + ($date == null ? 43 : $date.hashCode());
        final Object $title = this.getTitle();
        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $routeDocumentId = this.getRouteDocumentId();
        result = result * PRIME + ($routeDocumentId == null ? 43 : $routeDocumentId.hashCode());
        final Object $userDocumentId = this.getUserDocumentId();
        result = result * PRIME + ($userDocumentId == null ? 43 : $userDocumentId.hashCode());
        final Object $idERoute = this.getIdERoute();
        result = result * PRIME + ($idERoute == null ? 43 : $idERoute.hashCode());
        final Object $idEUser = this.getIdEUser();
        result = result * PRIME + ($idEUser == null ? 43 : $idEUser.hashCode());
        final Object $eRoute = this.getERoute();
        result = result * PRIME + ($eRoute == null ? 43 : $eRoute.hashCode());
        return result;
    }

    public String toString() {
        return "EActivity(super=" + super.toString() + ", startTime=" + this.getStartTime() + ", endTime=" + this.getEndTime() + ", kiloCalories=" + this.getKiloCalories() + ", date=" + this.getDate() + ", title=" + this.getTitle() + ", description=" + this.getDescription() + ", routeDocumentId=" + this.getRouteDocumentId() + ", userDocumentId=" + this.getUserDocumentId() + ", idERoute=" + this.getIdERoute() + ", idEUser=" + this.getIdEUser() + ", eRoute=" + this.getERoute() + ")";
    }
}


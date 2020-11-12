package com.myappdeport.model.entity.database.relationship;

import androidx.room.Embedded;
import androidx.room.Ignore;
import androidx.room.Relation;

import com.myappdeport.model.entity.database.EActivity;
import com.myappdeport.model.entity.database.ERoute;

public class ActivityAndRoute {
    @Embedded
    private EActivity eActivity;
    @Relation(
            parentColumn = "idERoute",
            entityColumn = "id",
            entity = ERoute.class
    )
    private ERoute eRoute;

    @Ignore
    public ActivityAndRoute(EActivity eActivity, ERoute eRoute) {
        this.eActivity = eActivity;
        this.eRoute = eRoute;
    }

    public ActivityAndRoute() {
    }

    public EActivity getEActivity() {
        return this.eActivity;
    }

    public ERoute getERoute() {
        return this.eRoute;
    }

    public void setEActivity(EActivity eActivity) {
        this.eActivity = eActivity;
    }

    public void setERoute(ERoute eRoute) {
        this.eRoute = eRoute;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ActivityAndRoute))
            return false;
        final ActivityAndRoute other = (ActivityAndRoute) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$eActivity = this.getEActivity();
        final Object other$eActivity = other.getEActivity();
        if (this$eActivity == null ? other$eActivity != null : !this$eActivity.equals(other$eActivity))
            return false;
        final Object this$eRoute = this.getERoute();
        final Object other$eRoute = other.getERoute();
        if (this$eRoute == null ? other$eRoute != null : !this$eRoute.equals(other$eRoute))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ActivityAndRoute;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $eActivity = this.getEActivity();
        result = result * PRIME + ($eActivity == null ? 43 : $eActivity.hashCode());
        final Object $eRoute = this.getERoute();
        result = result * PRIME + ($eRoute == null ? 43 : $eRoute.hashCode());
        return result;
    }

    public String toString() {
        return "ActivityAndRoute(eActivity=" + this.getEActivity() + ", eRoute=" + this.getERoute() + ")";
    }
}

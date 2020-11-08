package com.myappdeport.model.entity.database.relationship;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.myappdeport.model.entity.database.EPosition;
import com.myappdeport.model.entity.database.ERoute;

import java.util.List;
import java.util.Objects;

public class RouteWithPosition {
    @Embedded
    private ERoute route;
    @Relation(
            parentColumn = "id",
            entityColumn = "id",
            entity = EPosition.class
    )
    private List<EPosition> positions;

    public RouteWithPosition(ERoute route, List<EPosition> positions) {
        this.route = route;
        this.positions = positions;
    }

    public RouteWithPosition() {
    }

    public ERoute getRoute() {
        return this.route;
    }

    public List<EPosition> getPositions() {
        return this.positions;
    }

    public void setRoute(ERoute route) {
        this.route = route;
    }

    public void setPositions(List<EPosition> positions) {
        this.positions = positions;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof RouteWithPosition))
            return false;
        final RouteWithPosition other = (RouteWithPosition) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$route = this.getRoute();
        final Object other$route = other.getRoute();
        if (!Objects.equals(this$route, other$route))
            return false;
        final Object this$positions = this.getPositions();
        final Object other$positions = other.getPositions();
        if (!Objects.equals(this$positions, other$positions))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof RouteWithPosition;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $route = this.getRoute();
        result = result * PRIME + ($route == null ? 43 : $route.hashCode());
        final Object $positions = this.getPositions();
        result = result * PRIME + ($positions == null ? 43 : $positions.hashCode());
        return result;
    }

    public String toString() {
        return "RouteWithPosition(route=" + this.getRoute() + ", positions=" + this.getPositions() + ")";
    }
}

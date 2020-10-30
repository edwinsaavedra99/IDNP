package com.myappdeport.model.entity.database.relationship;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.myappdeport.model.entity.database.EPosition;
import com.myappdeport.model.entity.database.ERoute;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteWithPoint {
    @Embedded
    private ERoute route;
    @Relation(
            parentColumn = "id",
            entityColumn = "id",
            entity = EPosition.class
    )
    private List<EPosition> positions;
}

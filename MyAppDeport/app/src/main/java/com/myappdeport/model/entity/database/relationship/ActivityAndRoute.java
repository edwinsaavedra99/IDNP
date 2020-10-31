package com.myappdeport.model.entity.database.relationship;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.myappdeport.model.entity.database.EActivity;
import com.myappdeport.model.entity.database.ERoute;

import lombok.Data;

@Data
public class ActivityAndRoute {
    @Embedded
    private EActivity eActivity;
    @Relation(
            parentColumn = "id",
            entityColumn = "id",
            entity = ERoute.class
    )
    private ERoute eRoute;
}

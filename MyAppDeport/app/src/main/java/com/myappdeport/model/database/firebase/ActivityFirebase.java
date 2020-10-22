package com.myappdeport.model.database.firebase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Es la actividad que se almacenara en Firebase
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ActivityFirebase extends FirebaseEntity {
    private String startTime;
    private String endTime;
    private Integer kiloCalories;
    private RouteFirebase routeFirebase;
}

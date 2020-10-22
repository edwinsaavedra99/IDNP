package com.myappdeport.model.database.firebase;

import com.google.firebase.firestore.Exclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteFirebase {
    @Exclude
    private String documentId;
    private List<PointFirebase> pointFirebaseList;
    private Double totalDistance;
    private Double rhythm;

    public RouteFirebase(List<PointFirebase> pointFirebaseList, Double totalDistance, Double rhythm) {
        this.pointFirebaseList = pointFirebaseList;
        this.totalDistance = totalDistance;
        this.rhythm = rhythm;
    }
}

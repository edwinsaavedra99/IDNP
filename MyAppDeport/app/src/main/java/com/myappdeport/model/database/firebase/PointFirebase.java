package com.myappdeport.model.database.firebase;

import com.google.firebase.firestore.Exclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointFirebase {
    @Exclude
    private String documentId;
    private Double latitude;
    private Double longitude;
    private Double distance;

    public PointFirebase(Double latitude, Double longitude, Double distance) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }
}

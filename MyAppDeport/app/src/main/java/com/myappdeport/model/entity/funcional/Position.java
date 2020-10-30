package com.myappdeport.model.entity.funcional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Position {
    private Long id;
    private String documentId;
    private Double latitude;
    private Double longitude;
    private Double distance;

    public Position(Double latitude, Double longitude, Double distance) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }
}

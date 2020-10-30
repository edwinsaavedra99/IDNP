package com.myappdeport.model.entity.funcional;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Route {
    private Long id;
    private String documentId;
    private Double totalDistance;
    private Double rhythm;
    private List<Position> positionList;

    public Route(Double totalDistance, Double rhythm) {
        this.totalDistance = totalDistance;
        this.rhythm = rhythm;
    }

    public Route(Double totalDistance, Double rhythm, List<Position> positionList) {
        this(totalDistance, rhythm);
        this.positionList = positionList;
    }
}

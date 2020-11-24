package com.myappdeport.model.entity.functional;

import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Route extends Entity {
    private Double totalDistance;
    private Double rhythm;
    private List<Position> positionList;
}

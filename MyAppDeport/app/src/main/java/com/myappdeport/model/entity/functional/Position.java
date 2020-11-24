package com.myappdeport.model.entity.functional;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Position extends Entity {
    private Double latitude;
    private Double longitude;
    private Double distance;
    private Long idERoute;
    private String eRouteDocumentId;
}

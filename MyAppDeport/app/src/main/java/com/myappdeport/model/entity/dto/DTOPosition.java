package com.myappdeport.model.entity.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DTOPosition extends DTOEntity {
    private String latitude;
    private String longitude;
    private String distance;
    private String idERoute;
    private String eRouteDocumentId;
}

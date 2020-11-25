package com.myappdeport.model.entity.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DTOActivity extends DTOEntity {
    private String startTime;
    private String endTime;
    private String kiloCalories;
    private String date;
    /**
     * Id del documento route firebase.
     */
    private String routeDocumentId;
    /**
     * Id del documento user firebase.
     */
    private String userDocumentId;
    /**
     * Id de la ruta SQLite.
     */
    private String idERoute;
    /**
     * Id del usuario SQLite.
     */
    private String idEUser;

    private DTORoute dtoRoute;
}

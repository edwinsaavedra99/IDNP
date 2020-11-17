package com.myappdeport.model.entity.dto;

import androidx.room.Ignore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOActivity {
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

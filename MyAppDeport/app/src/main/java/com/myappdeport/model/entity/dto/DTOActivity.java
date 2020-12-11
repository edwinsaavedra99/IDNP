package com.myappdeport.model.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private String title;
    private String description;
    private DTORoute dtoRoute;
    private String routeDocumentId;
    private String userDocumentId;
    private String idERoute;
    private String idEUser;
}

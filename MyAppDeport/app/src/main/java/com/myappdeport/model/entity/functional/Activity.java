package com.myappdeport.model.entity.functional;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Activity extends Entity {
    private LocalTime startTime;
    private LocalTime endTime;
    private Double kiloCalories;
    private LocalDate date;
    private String title;
    private String description;
    private Route route;
    private String routeDocumentId;
    private String userDocumentId;
    private Long idERoute;
    private Long idEUser;
}

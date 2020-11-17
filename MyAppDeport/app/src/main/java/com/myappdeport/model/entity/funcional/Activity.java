package com.myappdeport.model.entity.funcional;

import androidx.room.Ignore;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    private Long id;
    private String documentId;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private Double kiloCalories;
    private Route route;
    private String routeDocumentId;
    private String userDocumentId;
    private Long idERoute;
    private Long idEUser;
}

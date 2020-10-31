package com.myappdeport.model.entity.funcional;

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
    private Double kiloCalories;
    private Route route;
}

package com.myappdeport.model.entity.funcional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    private String startTime;
    private String endTime;
    private String kiloCalories;
    private Route route;
}

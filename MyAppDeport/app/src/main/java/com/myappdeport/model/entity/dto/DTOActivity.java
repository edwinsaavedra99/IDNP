package com.myappdeport.model.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOActivity {
    private String startTime;
    private String endTime;
    private Integer kiloCalories;
    private DTORoute dtoRoute;
}

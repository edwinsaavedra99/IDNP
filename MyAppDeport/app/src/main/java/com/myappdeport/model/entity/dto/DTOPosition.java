package com.myappdeport.model.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOPosition {
    private String latitude;
    private String longitude;
    private String distance;
}

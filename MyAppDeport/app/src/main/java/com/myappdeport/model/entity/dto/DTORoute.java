package com.myappdeport.model.entity.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTORoute {
    private String totalDistance;
    private String rhythm;
    private List<DTOPosition> dtoPositionList;
}

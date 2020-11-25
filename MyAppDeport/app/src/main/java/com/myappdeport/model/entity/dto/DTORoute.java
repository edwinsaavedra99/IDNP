package com.myappdeport.model.entity.dto;

import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DTORoute extends DTOEntity{
    private String totalDistance;
    private String rhythm;
    private List<DTOPosition> dtoPositionList;
}

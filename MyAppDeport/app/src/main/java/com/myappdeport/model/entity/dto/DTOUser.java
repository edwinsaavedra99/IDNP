package com.myappdeport.model.entity.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DTOUser extends DTOEntity {
    private String name;
    private String email;
    private String birthday;
    private String weight;
    private String age;
    private String height;
    private String isNew;
    private String isCreated;
    private String isAuthenticated;
    private List<DTOActivity> dtoActivities;
}

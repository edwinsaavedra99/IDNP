package com.myappdeport.model.entity.functional;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends Entity {
    private String name;
    private String email;
    private String birthday;
    private Double weight;
    private Integer age;
    private Double height;
    private Boolean isNew;
    private Boolean isCreated;
    private Boolean isAuthenticated;
    private List<Activity> activities;
}

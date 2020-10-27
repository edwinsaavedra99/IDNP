package com.myappdeport.model.entity.database.firebase;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PointFirebase extends FirebaseEntity {
    private Double latitude;
    private Double longitude;
    private Double distance;
}
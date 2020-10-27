package com.myappdeport.model.entity.database.firebase;

import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RouteFirebase extends FirebaseEntity {
    private Double totalDistance;
    private Double rhythm;
    /**
     * Lista de point firebase documentId
     */
    private List<String> pointFirebaseDocumentIdList;
}

package com.myappdeport.model.database.firebase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SongFirebase extends FirebaseEntity{
    private String name;
    private String author;
    private String duration;
    private String songRoute;
}

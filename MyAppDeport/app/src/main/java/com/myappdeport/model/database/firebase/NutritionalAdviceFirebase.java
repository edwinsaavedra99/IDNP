package com.myappdeport.model.database.firebase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.File;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class NutritionalAdviceFirebase extends FirebaseEntity {
    private String title;
    private String shortDescription;
    private String longDescription;
    private File image;

}

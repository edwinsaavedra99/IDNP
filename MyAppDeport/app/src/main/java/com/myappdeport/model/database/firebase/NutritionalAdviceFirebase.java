package com.myappdeport.model.database.firebase;

import com.google.firebase.firestore.Exclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.File;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class NutritionalAdviceFirebase {
    @Exclude
    private String documentId;
    private String title;
    private String shortDescription;
    private String longDescription;
    /*
        DUDA -> XD
     */
    private File file;

    public NutritionalAdviceFirebase(String title, String shortDescription, String longDescription, File file) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.file = file;
    }
}

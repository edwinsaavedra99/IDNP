package com.myappdeport.model.database.firebase;

import com.google.firebase.firestore.Exclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class FirebaseEntity {
    private String documentId;

    @Exclude
    public String getDocumentId() {
        return documentId;
    }
}

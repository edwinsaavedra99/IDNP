package com.myappdeport.model.entity.database;

import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.google.firebase.firestore.Exclude;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Son el formato que tendran todas las entidades.
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public abstract class EntityDatabase {
    /**
     * Es el identificador en la base de datos SQLite.
     * - Excluido de la base de datos Firebase.
     * - Incluido en la base de datos SQLite.
     */
    @PrimaryKey(autoGenerate = true)
    private Long id;
    /**
     * Es el identificador en la base de datos Firebase.
     * - Excluido de la base de datos Firebase.
     * - Excluido en la base de datos SQLite.
     */
    @Ignore
    private String documentId;

    @Exclude
    public String getDocumentId() {
        return this.documentId;
    }

    @Exclude
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}

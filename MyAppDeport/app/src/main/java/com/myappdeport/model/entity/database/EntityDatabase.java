package com.myappdeport.model.entity.database;

import androidx.annotation.NonNull;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.Exclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Son el formato que tendran todas las entidades.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class EntityDatabase {
    /**
     * Es el identificador en la base de datos SQLite.
     * - Excluido de la base de datos Firebase.
     * - Incluido en la base de datos SQLite.
     */
    @PrimaryKey(autoGenerate = true)
    protected Long id;
    /**
     * Es el identificador en la base de datos Firebase.
     * - Excluido de la base de datos Firebase.
     * - Excluido en la base de datos SQLite.
     */
    @Ignore
    protected String documentId;

    @Exclude
    public String getDocumentId() {
        return this.documentId;
    }

    @Exclude
    public Long getId() {
        return this.id;
    }
}

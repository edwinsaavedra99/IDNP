package com.myappdeport.model.entity.database;

import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.Exclude;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Son el formato que tendran todas las entidades.
 */
@AllArgsConstructor
@NoArgsConstructor
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

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof EntityDatabase)) return false;
        final EntityDatabase other = (EntityDatabase) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (!Objects.equals(this$id, other$id)) return false;
        final Object this$documentId = this.getDocumentId();
        final Object other$documentId = other.getDocumentId();
        return Objects.equals(this$documentId, other$documentId);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof EntityDatabase;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $documentId = this.getDocumentId();
        result = result * PRIME + ($documentId == null ? 43 : $documentId.hashCode());
        return result;
    }

    public String toString() {
        return "EntityDatabase(id=" + this.getId() + ", documentId=" + this.getDocumentId() + ")";
    }
}

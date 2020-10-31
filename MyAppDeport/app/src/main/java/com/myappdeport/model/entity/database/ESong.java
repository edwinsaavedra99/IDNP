package com.myappdeport.model.entity.database;

import androidx.room.Entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ESong extends EntityDatabase {
    private String name;
    private String author;
    private String duration;
    private String songRoute;

    /**
     * SQLite constructor
     *
     * @param id        Es el identificador en SQLite.
     * @param name      Es el nombre de la canción.
     * @param author    Es el author de la canción.
     * @param duration  Es la duración del la canción.
     * @param songRoute Es la ruta de la canción.
     */
    public ESong(Long id, String name, String author, String duration, String songRoute) {
        super(id, null);
        this.name = name;
        this.author = author;
        this.duration = duration;
        this.songRoute = songRoute;
    }

    public String getName() {
        return this.name;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getDuration() {
        return this.duration;
    }

    public String getSongRoute() {
        return this.songRoute;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setSongRoute(String songRoute) {
        this.songRoute = songRoute;
    }
}

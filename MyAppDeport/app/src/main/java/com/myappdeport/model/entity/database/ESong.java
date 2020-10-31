package com.myappdeport.model.entity.database;

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
}

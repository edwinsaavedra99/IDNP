package com.myappdeport.model.entity.database;

import androidx.room.Entity;

import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ESong extends EntityDatabase {
    private String name;
    private String album;
    private String artist;
    private String duration;
    private String songRoute;

    public ESong(Long id, String name, String album, String artist, String duration, String songRoute) {
        super(id, null);
        this.name = name;
        this.album = album;
        this.artist = artist;
        this.duration = duration;
        this.songRoute = songRoute;
    }

    public String getName() {
        return this.name;
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

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setSongRoute(String songRoute) {
        this.songRoute = songRoute;
    }
}

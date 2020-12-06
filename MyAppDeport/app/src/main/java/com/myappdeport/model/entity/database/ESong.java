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
    private String title;
    private String album;
    private String artist;
    private Long duration;
    private String songPath;

    public ESong(Long id, String title, String album, String artist, Long duration, String songPath) {
        super(id, null);
        this.title = title;
        this.album = album;
        this.artist = artist;
        this.duration = duration;
        this.songPath = songPath;
    }


    public String getTitle() {
        return this.title;
    }

    public String getAlbum() {
        return this.album;
    }

    public String getArtist() {
        return this.artist;
    }

    public Long getDuration() {
        return this.duration;
    }

    public String getSongPath() {
        return this.songPath;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public void setSongPath(String songPath) {
        this.songPath = songPath;
    }
}

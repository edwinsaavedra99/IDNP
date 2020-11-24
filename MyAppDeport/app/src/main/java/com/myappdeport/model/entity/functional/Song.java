package com.myappdeport.model.entity.functional;

import android.net.Uri;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Song extends Entity {
    private String name;
    private String album;
    private String artist;
    private String duration;
    private Uri songRoute;

    public Song(Long id, String name, String album, String artist, String duration, Uri songRoute) {
        super(id, null);
        this.name = name;
        this.album = album;
        this.artist = artist;
        this.duration = duration;
        this.songRoute = songRoute;
    }
}

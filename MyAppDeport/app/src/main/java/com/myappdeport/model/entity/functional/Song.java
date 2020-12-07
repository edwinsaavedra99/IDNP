package com.myappdeport.model.entity.functional;

import android.net.Uri;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Song extends Entity {
    private String title;
    private String album;
    private String artist;
    private Long duration;
    private Uri songPath;

    public Song(Long id, String title, String album, String artist, Long duration, Uri songPath) {
        this.id = id;
        this.title = title;
        this.album = album;
        this.artist = artist;
        this.duration = duration;
        this.songPath = songPath;
    }
}

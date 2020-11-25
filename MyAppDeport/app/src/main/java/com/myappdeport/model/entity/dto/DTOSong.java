package com.myappdeport.model.entity.dto;

import android.os.Parcel;
import android.os.Parcelable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DTOSong extends DTOEntity implements Parcelable {
    private String name;
    private String author;
    private String album;
    private String artist;
    private String duration;
    private String songRoute;

    protected DTOSong(Parcel in) {
        name = in.readString();
        author = in.readString();
        album = in.readString();
        artist = in.readString();
        duration = in.readString();
        songRoute = in.readString();
    }

    public static final Creator<DTOSong> CREATOR = new Creator<DTOSong>() {
        @Override
        public DTOSong createFromParcel(Parcel in) {
            return new DTOSong(in);
        }

        @Override
        public DTOSong[] newArray(int size) {
            return new DTOSong[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(author);
        dest.writeString(album);
        dest.writeString(artist);
        dest.writeString(duration);
        dest.writeString(songRoute);
    }
}

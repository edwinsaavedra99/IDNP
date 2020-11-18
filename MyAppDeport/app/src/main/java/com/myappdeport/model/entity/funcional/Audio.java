package com.myappdeport.model.entity.funcional;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import lombok.Getter;
import lombok.Setter;


public class Audio implements Parcelable {
    private String path;
    private String name;
    private int id;
    private int numOfSong;
    private String album;
    private String artist;

    public Audio(){}
    public Audio(Parcel in){
        this.path = in.readString();
        this.name = in.readString();
        this.id = in.readInt();
        this.numOfSong = in.readInt();
        this.album = in.readString();
        this.artist = in.readString();
    }

    public static final Creator<Audio> CREATOR = new Creator<Audio>() {
        @Override
        public Audio createFromParcel(Parcel in) {
            return new Audio(in);
        }
        @Override
        public Audio[] newArray(int size) {
            return new Audio[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(path);
        parcel.writeString(name);
        parcel.writeInt(id);
        parcel.writeInt(numOfSong);
        parcel.writeString(album);
        parcel.writeString(artist);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumOfSong() {
        return numOfSong;
    }

    public void setNumOfSong(int numOfSong) {
        this.numOfSong = numOfSong;
    }
}

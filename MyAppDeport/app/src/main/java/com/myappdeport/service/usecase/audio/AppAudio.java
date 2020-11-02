package com.myappdeport.service.usecase.audio;

import android.app.Application;
import android.media.MediaPlayer;

public class AppAudio extends Application {
    public static final String CHANNEL_ID = "exampleServiceChannel";
    public static final String CLICKED = "click";

    private static MediaPlayer mediaPlayer;

    public static MediaPlayer getInstance() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        return mediaPlayer;
    }
    private static AppAudio appClass;

    public static synchronized AppAudio getObject() {
        return appClass;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appClass = this;
    }



}

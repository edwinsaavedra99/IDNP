package com.myappdeport.service.usecase.audio;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Parcelable;
import android.util.Log;

import com.myappdeport.R;

import java.util.ArrayList;

import static com.myappdeport.service.usecase.audio.AudioService.audios;
import static com.myappdeport.service.usecase.audio.AudioService.notificationLayout;
import static com.myappdeport.service.usecase.audio.AudioService.num;
//import static com.myappdeport.view.fragments.MusicPlayer.Duration;
import static com.myappdeport.view.fragments.MusicPlayer.mediaPlayer;
import static com.myappdeport.view.fragments.MusicPlayer.updateUi;
import static com.myappdeport.view.fragments.MusicPlayer.tvSongName;
//import static com.myappdeport.view.fragments.MusicPlayer.time;
import static com.myappdeport.view.fragments.MusicPlayer.textTime;
import static com.myappdeport.view.fragments.MusicPlayer.mUpdateTime;

public class ReciverEvents extends BroadcastReceiver {
    public static Boolean btn = false;
    @SuppressLint("SetTextI18n")
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getStringExtra("Clicked");
        if (action != null) {
            switch (action) {
                case "Close":
                    Log.d("LOG", "CLOSED CLOSED CLOSED");
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                    tvSongName.setText("No song playing! \n");
                    textTime.setText("00:00");
                    updateUi(true,1);
                    Intent inte = new Intent(context, AudioService.class);
                    context.stopService(inte);
                    break;
                case "Pause":
                    Log.d("LOG", "Pause Pause Pause");
                    if (mediaPlayer != null) {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.pause();
                            updateUi(true,0);
                            btn = true;
                            Log.d("Hello", "Enter");
                        } else {
                            textTime.post(mUpdateTime);
                            mediaPlayer.start();
                            updateUi(false,0);
                            btn = false;
                            Log.d("Hello", "Enter too");
                        }
                    }
                    break;
                case "Next":
                    Log.d("LOG", "Next Next Next");
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        if (num == audios.size() - 1) {
                            num = 0;
                        } else {
                            num++;
                        }
                        mediaPlayer = MediaPlayer.create(context, Uri.parse(audios.get(num).getPath()));
                        tvSongName.setText(audios.get(num).getName());
                        textTime.post(mUpdateTime);
                        mediaPlayer.start();
                        notificationLayout.setImageViewResource(R.id.stop, R.drawable.ic_baseline_pause_circle_filled_24);
                        Intent in = new Intent(AppAudio.getObject(), AudioService.class);
                        in.putExtra("song_name", audios.get(num).getName());
                        Log.d("ELLOS", audios.get(num).getName());
                        in.putExtra("num_of_songs", num);
                        in.putParcelableArrayListExtra("songs", (ArrayList<? extends Parcelable>) audios);
                        context.startService(in);
                    }
                    break;
                case "Previous":
                    Log.d("LOG", "Previous Previous Previous");
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        if (num == 0) {
                            num = audios.size() - 1;
                        } else {
                            num--;
                        }
                        textTime.post(mUpdateTime);
                        mediaPlayer = MediaPlayer.create(context, Uri.parse(audios.get(num).getPath()));
                        tvSongName.setText(audios.get(num).getName());
                        mediaPlayer.start();
                        notificationLayout.setImageViewResource(R.id.stop, R.drawable.ic_baseline_pause_circle_filled_24);
                        Intent in = new Intent(AppAudio.getObject(), AudioService.class);
                        in.putExtra("song_name", audios.get(num).getName());
                        Log.d("ELLOS", audios.get(num).getName());
                        in.putExtra("num_of_songs", num);
                        in.putParcelableArrayListExtra("songs", (ArrayList<? extends Parcelable>) audios);
                        context.startService(in);
                    }
                    break;
            }
        }
    }
}

package com.myappdeport.view.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.myappdeport.R;
import com.myappdeport.model.entity.funcional.Audio;
import com.myappdeport.service.usecase.audio.AppAudio;
import com.myappdeport.service.usecase.audio.AudioService;
import com.myappdeport.view.adapters.AdapterSong;
import com.myappdeport.viewmodel.AudioViewModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import static com.myappdeport.service.usecase.audio.AudioService.manager;
import static com.myappdeport.service.usecase.audio.AudioService.notification;
import static com.myappdeport.service.usecase.audio.AudioService.notificationLayout;
import static com.myappdeport.service.usecase.audio.AudioService.num;
import static com.myappdeport.utils.ParseMetrics.milliSecondsToTimer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MusicPlayer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MusicPlayer extends Fragment {
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    List<Audio> audioList = new ArrayList<Audio>();
    RecyclerView recyclerView;
    AdapterSong adapter;
    private String[] STAR = {"*"};
    Cursor cursor;
    Uri allsongsuri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
    public static MediaPlayer mediaPlayer;
    public AudioViewModel audioViewModel;
    public static Intent intent;
    @SuppressLint("StaticFieldLeak")
    private static ImageView ivPause;
    @SuppressLint("StaticFieldLeak")
    private static SeekBar mSeekBar;
    int seekPostion;
    @SuppressLint("StaticFieldLeak")
    public static TextView tvSongName;
    AudioManager audioManager;
    @SuppressLint("StaticFieldLeak")
    public static TextView textTime;
    SettingsContentObserver mSettingsContentObserver;
    public static int numOfSong;

    public MusicPlayer() {
        // Required empty public constructor
    }

    public static MusicPlayer newInstance(String param1, String param2) {
        MusicPlayer fragment = new MusicPlayer();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_music_player, container, false);
        textTime = viewGroup.findViewById(R.id.timer);
        recyclerView = viewGroup.findViewById(R.id.rvSongs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        intent = new Intent(getActivity(), AudioService.class);
        audioViewModel = new AudioViewModel();
        ImageView ivNext = viewGroup.findViewById(R.id.next);
        ivPause = viewGroup.findViewById(R.id.play);
        ImageView ivPrevious = viewGroup.findViewById(R.id.previous);
        mSeekBar = viewGroup.findViewById(R.id.seekBar2);
        audioManager = (AudioManager) Objects.requireNonNull(getActivity()).getSystemService(Context.AUDIO_SERVICE);
        tvSongName = viewGroup.findViewById(R.id.nameSong);
        if (checkPermissionREAD_EXTERNAL_STORAGE(getContext())) {
            //Search Music in External Storage - use a cursor
            cursor = getActivity().getContentResolver().query(allsongsuri, STAR, selection, null, null);
            adapter = new AdapterSong(getContext(), audioList);
            recyclerView.setAdapter(adapter);
            audioViewModel.getAudios(cursor).observe(getActivity(), audio -> {
                audioList.clear();
                audioList.addAll(audio);
                adapter.notifyDataSetChanged();
            });
        }

        adapter.setOnSongListener(position -> {
            // initialize Uri here
            stopPlaying();
            getActivity().stopService(intent);
            mediaPlayer = AppAudio.getInstance();
            mediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(audioList.get(position).getPath()));
            mSeekBar.setMax(mediaPlayer.getDuration() / 1000);
            tvSongName.setText(audioList.get(position).getName());
            textTime.post(mUpdateTime);
            mediaPlayer.start();
            ivPause.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
            intent.putExtra("song_name", audioList.get(position).getName());
            numOfSong = audioList.get(position).getNumOfSong();
            intent.putExtra("num_of_songs", audioList.get(position).getNumOfSong());
            intent.putParcelableArrayListExtra("songs", (ArrayList<? extends Parcelable>) audioList);
            getActivity().startService(intent);
            ContextCompat.startForegroundService(getActivity(), intent);
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    try {
                        mSeekBar.setProgress(mediaPlayer.getCurrentPosition() / 1000);
                    } catch (Exception ignored) {}
                }
            }
        }, 0, 500);

        ivPause.setOnClickListener(view -> {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                seekPostion = mediaPlayer.getCurrentPosition();
                updateUi(mediaPlayer.isPlaying(), 0);
                ivPause.setImageResource(R.drawable.ic_play);
            } else {
                if (mediaPlayer != null) {
                    updateUi(!mediaPlayer.isPlaying(), 0);
                    mSeekBar.setMax(mediaPlayer.getDuration() / 1000);
                    textTime.post(mUpdateTime);
                    mediaPlayer.start();
                    ivPause.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
                }
            }
        });
        ivNext.setOnClickListener(view -> {
            if (mediaPlayer != null) {
                stopPlaying();
                if (num == audioList.size() - 1) { num = 0; } else { num++; }
                mediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(audioList.get(num).getPath()));
                tvSongName.setText(audioList.get(num).getName());
                textTime.post(mUpdateTime);
                mediaPlayer.start();
                mSeekBar.setMax(mediaPlayer.getDuration() / 1000);
                ivPause.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
                Intent intentNext = new Intent(getActivity(), AudioService.class);
                intentNext.putExtra("song_name", audioList.get(num).getName());
                intentNext.putExtra("num_of_songs", num);
                intentNext.putParcelableArrayListExtra("songs", (ArrayList<? extends Parcelable>) audioList);
                getActivity().startService(intentNext);
            }
        });
        ivPrevious.setOnClickListener(view -> {
            if (mediaPlayer != null) {
                stopPlaying();
                if (num == 0) {
                    num = audioList.size() - 1;
                } else {
                    num--;
                }
                mediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(audioList.get(num).getPath()));
                tvSongName.setText(audioList.get(num).getName());
                textTime.post(mUpdateTime);
                mediaPlayer.start();
                ivPause.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
                Intent intentPrev = new Intent(getActivity(), AudioService.class);
                intentPrev.putExtra("song_name", audioList.get(num).getName());
                intentPrev.putExtra("num_of_songs", num);
                intentPrev.putParcelableArrayListExtra("songs", (ArrayList<? extends Parcelable>) audioList);
                getActivity().startService(intentPrev);
            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b && mediaPlayer != null) {
                    mSeekBar.setMax(mediaPlayer.getDuration() / 1000);
                    mediaPlayer.seekTo(i * 1000);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        mSettingsContentObserver = new SettingsContentObserver(new Handler());
        Objects.requireNonNull(getActivity()).getApplicationContext().getContentResolver().registerContentObserver(
                android.provider.Settings.System.CONTENT_URI, true,
                mSettingsContentObserver);
        // Inflate the layout for this fragment
        return viewGroup;
    }
    //stop Song - mediaPlayer stopped
    public void stopPlaying() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,Manifest.permission.READ_EXTERNAL_STORAGE);
                } else {
                    ActivityCompat.requestPermissions((Activity) context,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public void showDialog(final String msg, final Context context,final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                (dialog, which) -> ActivityCompat.requestPermissions((Activity) context,
                        new String[]{permission},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE));
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // do your stuff
            } else {
                Toast.makeText(getActivity(), "GET_ACCOUNTS Denied", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions,grantResults);
        }
    }

    public static void updateUi(boolean bool, int x) {
        if (bool) {
            ivPause.setImageResource(R.drawable.ic_play);
            notificationLayout.setImageViewResource(R.id.stop, R.drawable.ic_play);
            manager.notify(1, notification);
        } else {
            ivPause.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
            notificationLayout.setImageViewResource(R.id.stop, R.drawable.ic_baseline_pause_circle_filled_24);
            manager.notify(1, notification);
        }
        if (x == 1) {
            mSeekBar.setProgress(0);
        }
    }
    public static class SettingsContentObserver extends ContentObserver {
        public SettingsContentObserver(Handler handler) {
            super(handler);
        }
        @Override
        public boolean deliverSelfNotifications() {
            return super.deliverSelfNotifications();
        }
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Log.v("ASDR", "Settings change detected");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Objects.requireNonNull(getActivity()).getApplicationContext().getContentResolver().unregisterContentObserver(mSettingsContentObserver);

    }

    public static Runnable mUpdateTime = new Runnable() {
        public void run() {
            int currentDuration;
            if (mediaPlayer !=null ){
                if (mediaPlayer.isPlaying()) {
                    currentDuration = mediaPlayer.getCurrentPosition();
                    updatePlayer(currentDuration);
                    textTime.postDelayed(this, 1000);
                }else {
                    textTime.removeCallbacks(this);
                }
            }
        }
    };

    @SuppressLint("SetTextI18n")
    public static void updatePlayer(int currentDuration){
        textTime.setText("" + milliSecondsToTimer(currentDuration));
    }
}

package com.myappdeport.view.fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.myappdeport.R;
import com.myappdeport.model.entity.functional.Song;
import com.myappdeport.service.usecase.media.MediaPlayBackService;
import com.myappdeport.view.activitys.MainActivity;
import com.myappdeport.view.adapters.MusicAdapter;
import com.myappdeport.viewmodel.MusicViewModel;

import java.util.*;

public class MusicPlayer extends Fragment {
    private static final String TAG = MusicPlayer.class.getSimpleName();
    private MediaBrowserCompat mediaBrowserCompat;
    private ImageView btnPlayPause;
    private TextView titleSong;
    private ImageView btnNext;
    private ImageView btnStop;
    private ImageView btnPrev;
    private ImageView btnRefresh;
    private SeekBar musicProgress;
    private SearchView searchMusic;
    private RecyclerView listMusicView;
    private MusicAdapter musicAdapter;

    private MediaPlayBackService mediaService;
    private boolean mBound = false;

    private MusicViewModel musicViewModel;
    private MediaMetadataCompat currentMetadata;

    private final ServiceConnection connection = new ServiceConnection() {

        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            MediaPlayBackService.MediaDataBinder binder = (MediaPlayBackService.MediaDataBinder) service;
            mediaService = binder.getMediaPlaybackService();
            mBound = true;
            // Add to services to media service.
            mediaService.setMediaQueue(musicViewModel.getAllSongs(MusicPlayer.this.getActivity()).getValue().subList(0, 100));
            currentMetadata = mediaService.getCurrentMetadata();
            if (currentMetadata != null) {
                titleSong.setText(currentMetadata.getText(MediaMetadataCompat.METADATA_KEY_TITLE));
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    public MediaControllerCompat.Callback mediaControllerCallback = new MediaControllerCompat.Callback() {
        private final String TAG = MediaControllerCompat.Callback.class.getSimpleName();
        boolean isEnable = true;

        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat state) {
            super.onPlaybackStateChanged(state);
            Log.e(TAG, "PlaybackStateChange: " + state);
            isEnable = state.getState() != PlaybackStateCompat.STATE_PAUSED;
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            super.onMetadataChanged(metadata);
            Log.e(TAG, "SetMetadataChange: " + metadata.getDescription());
            titleSong.setText(metadata.getText(MediaMetadataCompat.METADATA_KEY_TITLE));
            int totalDuration = (int) metadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION);
            musicProgress.setMax(totalDuration / 1000);
            musicProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @RequiresApi(api = Build.VERSION_CODES.Q)
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    if (b && mediaService.getCurrentPosition() != -1) {
                        musicProgress.setMax(totalDuration / 1000);
                        MediaControllerCompat.getMediaController(Objects.requireNonNull(MusicPlayer.this.getActivity())).getTransportControls().seekTo(i * 1000);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
            musicProgress.setProgress(0);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Objects.requireNonNull(getActivity()).setContentView(R.layout.activity_main);
        //this.connectionCallback = new MediaBrowserConnectionCallback();
        this.mediaBrowserCompat = new MediaBrowserCompat(Objects.requireNonNull(this.getActivity()).getApplicationContext(),
                new ComponentName(this.getActivity(), MediaPlayBackService.class),
                connectionCallback,
                null);
        this.musicViewModel = new ViewModelProvider(this).get(MusicViewModel.class);
        this.musicAdapter = new MusicAdapter(this.getActivity(), Collections.emptyList());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_music_player, container, false);
        this.btnPlayPause = viewGroup.findViewById(R.id.play);
        this.btnNext = viewGroup.findViewById(R.id.next);
        this.btnPrev = viewGroup.findViewById(R.id.previous);
        this.btnStop = viewGroup.findViewById(R.id.stop);
        this.btnRefresh = viewGroup.findViewById(R.id.refresh);
        this.musicProgress = viewGroup.findViewById(R.id.progress_music);
        this.searchMusic = viewGroup.findViewById(R.id.searchMusic);
        this.listMusicView = viewGroup.findViewById(R.id.rvSongs);
        this.listMusicView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        this.titleSong = viewGroup.findViewById(R.id.nameSong);
        this.initActionOfMediaControls();
        //this.musicViewModel.getAllSongs(Objects.requireNonNull(this.getActivity()).getApplicationContext()).observe(getViewLifecycleOwner(), this::updateListMusicView);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (mBound && mediaService.getCurrentPosition() != -1) {
                    try {
                        musicProgress.setProgress(mediaService.getCurrentPosition() / 1000);
                    } catch (Exception e){
                        Log.e(TAG, "ERROR SYNC");
                    }
                }
            }
        }, 0, 500);

        return viewGroup;
    }

    /**
     * Initial some components of media player.
     */
    public void initActionOfMediaControls() {
        this.btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBound) {
                    List<Song> songList = musicViewModel.getAllSongs(MusicPlayer.this.getActivity()).getValue();
                    // Actualizando la cola en el servicio.
                    mediaService.setMediaQueue(songList);
                    // Actualizando la vista en el fragment.
                    updateListMusicView(songList);
                }
            }
        });
    }

    /**
     * Update Component
     */
    private void updateListMusicView(List<Song> songs) {
        this.musicViewModel.getAllSongs(this.getActivity()).observe(getViewLifecycleOwner(), songsLiveData -> {
            musicAdapter.setSongs(songsLiveData.subList(0, 100));
            musicAdapter.notifyDataSetChanged();
        });
        this.listMusicView.setAdapter(musicAdapter);
        this.musicAdapter.setOnClickListener(new MusicAdapter.MusicClickListener() {
            @Override
            public void onClick(View v) {
                if (mBound) {
                    MediaControllerCompat.getMediaController(Objects.requireNonNull(MusicPlayer.this.getActivity())).getTransportControls().playFromMediaId(listMusicView.getChildAdapterPosition(v) + "", null);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mediaBrowserCompat.connect();

        Intent intent = new Intent(this.getActivity(), MediaPlayBackService.class);
        Objects.requireNonNull(this.getActivity()).bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(this.getActivity()).setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void onStop() {
        Log.e(TAG, "Stop Main Activity");
        super.onStop();
        if (MediaControllerCompat.getMediaController(Objects.requireNonNull(this.getActivity())) != null) {
            MediaControllerCompat.getMediaController(this.getActivity()).unregisterCallback(mediaControllerCallback);
        }
        mediaBrowserCompat.disconnect();

        this.getActivity().unbindService(connection);
        mBound = false;
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "Destroy Main Activity");
        super.onDestroy();
    }

    private final MediaBrowserCompat.ConnectionCallback connectionCallback = new MediaBrowserCompat.ConnectionCallback() {

        @Override
        public void onConnected() {
            super.onConnected();
            MediaSessionCompat.Token token = mediaBrowserCompat.getSessionToken();
            MediaControllerCompat mediaController = new MediaControllerCompat(MusicPlayer.this.getActivity(), token);
            MediaControllerCompat.setMediaController(Objects.requireNonNull(MusicPlayer.this.getActivity()), mediaController);
            // Finish building the UI
            buildTransportControls();
        }

        @Override
        public void onConnectionSuspended() {
            super.onConnectionSuspended();
        }

        @Override
        public void onConnectionFailed() {
            super.onConnectionFailed();
        }
    };

    private void buildTransportControls() {
        this.btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pdState = MediaControllerCompat.getMediaController(Objects.requireNonNull(MusicPlayer.this.getActivity())).getPlaybackState().getState();
                if (pdState == PlaybackStateCompat.STATE_PLAYING)
                    MediaControllerCompat.getMediaController(MusicPlayer.this.getActivity()).getTransportControls().pause();
                if (pdState == PlaybackStateCompat.STATE_PAUSED || pdState == PlaybackStateCompat.STATE_CONNECTING || pdState == PlaybackStateCompat.STATE_STOPPED)
                    MediaControllerCompat.getMediaController(MusicPlayer.this.getActivity()).getTransportControls().play();
            }
        });
        this.btnNext.setOnClickListener(v -> MediaControllerCompat.getMediaController(Objects.requireNonNull(MusicPlayer.this.getActivity())).getTransportControls().skipToNext());

        this.btnPrev.setOnClickListener(v -> MediaControllerCompat.getMediaController(Objects.requireNonNull(MusicPlayer.this.getActivity())).getTransportControls().skipToPrevious());

        this.btnStop.setOnClickListener(v -> MediaControllerCompat.getMediaController(Objects.requireNonNull(MusicPlayer.this.getActivity())).getTransportControls().stop());

        MediaControllerCompat mediaController = MediaControllerCompat.getMediaController(Objects.requireNonNull(MusicPlayer.this.getActivity()));
        //MediaMetadataCompat metadata = mediaController.getMetadata();
//        PlaybackStateCompat pbState = mediaController.getPlaybackState();
        mediaController.registerCallback(mediaControllerCallback);
    }
}

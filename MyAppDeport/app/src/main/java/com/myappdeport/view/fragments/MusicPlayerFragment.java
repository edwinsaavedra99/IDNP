package com.myappdeport.view.fragments;
/*
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.SeekBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.myappdeport.R;
import com.myappdeport.model.entity.functional.Song;
import com.myappdeport.service.usecase.media.MediaPlayBackService;
import com.myappdeport.view.activitys.MainActivity;
import com.myappdeport.view.adapters.MusicAdapter;
import com.myappdeport.viewmodel.MusicViewModel;

import java.util.List;
import java.util.Objects;

public class MusicPlayerFragment extends Fragment {
    private static final String TAG = MusicPlayerFragment.class.getSimpleName();
    private MediaBrowserCompat mediaBrowserCompat;
    private ImageView btnPlayPause;
    private ImageView btnNext;
    private ImageView btnStop;
    private ImageView btnPrev;
    private ImageView btnRefresh;
    private SeekBar musicProgress;
    private SearchView searchMusic;
    private RecyclerView listMusicView;

    private MediaPlayBackService mediaService;
    private boolean mBound = false;

    private MusicViewModel musicViewModel;

    private final ServiceConnection connection = new ServiceConnection() {

        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            MediaPlayBackService.MediaDataBinder binder = (MediaPlayBackService.MediaDataBinder) service;
            mediaService = binder.getMediaPlaybackService();
            mBound = true;
            // Add to services to media service.
            mediaService.setMediaQueue(musicViewModel.getAllSongs(MusicPlayerFragment.this.getActivity()).getValue());
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    public MediaControllerCompat.Callback mediaControllerCallback = new MediaControllerCompat.Callback() {
        private final String TAG = MediaControllerCompat.Callback.class.getSimpleName();

        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat state) {
            super.onPlaybackStateChanged(state);
            Log.e(TAG, "PlaybackStateChange: " + state);

        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            super.onMetadataChanged(metadata);
            Log.e(TAG, "SetMetadataChange: " + metadata.getDescription().getTitle());
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getActivity()).setContentView(R.layout.activity_main);
        //this.connectionCallback = new MediaBrowserConnectionCallback();
        this.mediaBrowserCompat = new MediaBrowserCompat(this.getActivity().getApplicationContext(),
                new ComponentName(this.getActivity(), MediaPlayBackService.class),
                connectionCallback,
                null);
        this.musicViewModel = new ViewModelProvider(this).get(MusicViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_music_player, container, false);
        this.btnPlayPause = viewGroup.findViewById(R.id.play);
        this.btnNext = viewGroup.findViewById(R.id.next);
        this.btnPrev = viewGroup.findViewById(R.id.previous);
        this.btnRefresh = viewGroup.findViewById(R.id.refresh);
        this.musicProgress = viewGroup.findViewById(R.id.progress_music);
        this.searchMusic = viewGroup.findViewById(R.id.searchMusic);
        this.listMusicView = viewGroup.findViewById(R.id.rvSongs);
        this.initActionOfMediaControls();
        return viewGroup;
    }

    /**
     * Initial some components of media player.
     *//*
    public void initActionOfMediaControls() {
        this.btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBound) {
                    List<Song> songList = musicViewModel.getAllSongs(MusicPlayerFragment.this.getActivity()).getValue();
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
/*
    private void updateListMusicView(List<Song> songs) {
        MusicAdapter musicAdapter = new MusicAdapter(this.getActivity(), songs);
        this.musicViewModel.getAllSongs(this.getActivity()).observe(this, songsLiveData -> {
            musicAdapter.setSongs(songsLiveData);
            musicAdapter.notifyDataSetChanged();
        });
        this.listMusicView.setAdapter(musicAdapter);
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
            MediaControllerCompat mediaController = new MediaControllerCompat(MusicPlayerFragment.this.getActivity(), token);
            MediaControllerCompat.setMediaController(Objects.requireNonNull(MusicPlayerFragment.this.getActivity()), mediaController);
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
                int pdState = MediaControllerCompat.getMediaController(Objects.requireNonNull(MusicPlayerFragment.this.getActivity())).getPlaybackState().getState();
                if (pdState == PlaybackStateCompat.STATE_PLAYING)
                    MediaControllerCompat.getMediaController(MusicPlayerFragment.this.getActivity()).getTransportControls().pause();
                if (pdState == PlaybackStateCompat.STATE_PAUSED || pdState == PlaybackStateCompat.STATE_CONNECTING || pdState == PlaybackStateCompat.STATE_STOPPED)
                    MediaControllerCompat.getMediaController(MusicPlayerFragment.this.getActivity()).getTransportControls().play();
            }
        });
        this.btnNext.setOnClickListener(v -> MediaControllerCompat.getMediaController(Objects.requireNonNull(MusicPlayerFragment.this.getActivity())).getTransportControls().skipToNext());

        this.btnPrev.setOnClickListener(v -> MediaControllerCompat.getMediaController(Objects.requireNonNull(MusicPlayerFragment.this.getActivity())).getTransportControls().skipToPrevious());

        this.btnStop.setOnClickListener(v -> MediaControllerCompat.getMediaController(Objects.requireNonNull(MusicPlayerFragment.this.getActivity())).getTransportControls().stop());

        MediaControllerCompat mediaController = MediaControllerCompat.getMediaController(Objects.requireNonNull(MusicPlayerFragment.this.getActivity()));
//        MediaMetadataCompat metadata = mediaController.getMetadata();
//        PlaybackStateCompat pbState = mediaController.getPlaybackState();
        mediaController.registerCallback(mediaControllerCallback);
    }

}
*/
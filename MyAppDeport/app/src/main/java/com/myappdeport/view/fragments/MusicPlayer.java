package com.myappdeport.view.fragments;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;

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
import com.myappdeport.view.adapters.MusicAdapter;
import com.myappdeport.viewmodel.MusicViewModel;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static com.myappdeport.utils.ParseMetrics.milliSecondsToTimer;

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
    private TextView textTimer;
    private ImageView albumArt;

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
            musicViewModel.getAllSongs(MusicPlayer.this.getActivity()).observe(MusicPlayer.this.getViewLifecycleOwner(), songs -> {
                mediaService.setMediaQueue(songs);
                currentMetadata = mediaService.getCurrentMetadata();
                if (currentMetadata != null) {
                    titleSong.setText(currentMetadata.getText(MediaMetadataCompat.METADATA_KEY_TITLE));
                    albumArt.setImageBitmap(currentMetadata.getBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART));
                }
            });
            //mediaService.setMediaQueue(musicViewModel.getAllSongs(MusicPlayer.this.getActivity()).getValue().subList(0, 100));

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

            if (state.getState() != PlaybackStateCompat.STATE_PAUSED && state.getState() != PlaybackStateCompat.STATE_STOPPED) {
                btnPlayPause.setImageResource(R.drawable.ic_pause);
            }
            if (state.getState() != PlaybackStateCompat.STATE_PLAYING) {
                btnPlayPause.setImageResource(R.drawable.ic_play);
            }
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            super.onMetadataChanged(metadata);
            Log.e(TAG, "SetMetadataChange: " + metadata.getDescription());
            titleSong.setText(metadata.getText(MediaMetadataCompat.METADATA_KEY_TITLE));
            int totalDuration = (int) metadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION);
            albumArt.setImageBitmap(metadata.getBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART));
            musicProgress.setMax(totalDuration / 1000);
            musicProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @RequiresApi(api = Build.VERSION_CODES.Q)
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    if (b && mediaService.getCurrentPosition() != -1) {
                        musicProgress.setMax(totalDuration / 1000);
                        MediaControllerCompat.getMediaController(Objects.requireNonNull(MusicPlayer.this.getActivity())).getTransportControls().seekTo(i * 1000L);
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

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @SuppressLint("SetTextI18n")
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
        this.textTimer = viewGroup.findViewById(R.id.timer);
        this.searchMusic = viewGroup.findViewById(R.id.searchMusic);
        this.albumArt = viewGroup.findViewById(R.id.album_art);
        this.searchMusic.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String queryNormative = query.toLowerCase().trim();
                AtomicBoolean queryResult = new AtomicBoolean(false);
                musicViewModel.getAllSongs(MusicPlayer.this.getContext()).observe(MusicPlayer.this.getViewLifecycleOwner(), songs -> {
                    List<Song> querySongs = songs
                            .parallelStream()
                            .filter(song -> song.getTitle().toLowerCase().contains(queryNormative)
                                    || song.getAlbum().toLowerCase().contains(queryNormative)
                                    || song.getArtist().toLowerCase().contains(queryNormative))
                            .collect(Collectors.toList());
                    musicAdapter.setSongs(querySongs);
                    mediaService.setMediaQueue(querySongs);
                    musicAdapter.notifyDataSetChanged();
                    queryResult.set(querySongs.isEmpty());
                });
                return queryResult.get();
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        this.initActionOfMediaControls();
        //this.musicViewModel.getAllSongs(Objects.requireNonNull(this.getActivity()).getApplicationContext()).observe(getViewLifecycleOwner(), this::updateListMusicView);
        this.updateListMusicView();
        this.titleSong.setText("Cargando su musica espere ...");
        this.albumArt.setImageResource(R.drawable.icon_music);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void run() {
                try {
                    if (mBound && mediaService.getCurrentPosition() != -1) {
                        musicProgress.post(() -> musicProgress.setProgress(mediaService.getCurrentPosition() / 1000));
                        textTimer.post(() -> textTimer.setText(milliSecondsToTimer(mediaService.getCurrentPosition())));
                    } else {
                        musicProgress.post(() -> musicProgress.setProgress(0));
                        textTimer.post(() -> textTimer.setText(milliSecondsToTimer(0)));
                    }
                } catch (Exception e) {
                    Log.e(TAG, "ERROR SYNC");
                }
            }
        }, 0, 500);

        return viewGroup;
    }

    /**
     * Initial some components of media player.
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void initActionOfMediaControls() {
        this.btnRefresh.setOnClickListener(v -> {
            Log.e(TAG, "udpate Reclycler");
            if (mBound) {
                Log.e(TAG, "Reclycler updating");
                musicViewModel.getAllSongs(MusicPlayer.this.getActivity()).observe(MusicPlayer.this.getViewLifecycleOwner(), songs -> {
                    musicAdapter.setSongs(songs);
                    musicAdapter.notifyDataSetChanged();
                    // Actualizando la cola en el servicio.
                    mediaService.setMediaQueue(songs);
                    // Actualizando la vista en el fragment.
                    updateListMusicView();
                });
            }
        });
    }

    /**
     * Update Component
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void updateListMusicView() {
        this.musicViewModel.getAllSongs(this.getActivity()).observe(getViewLifecycleOwner(), songsLiveData -> {
            musicAdapter.setSongs(songsLiveData);
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
        int state = MediaControllerCompat.getMediaController(Objects.requireNonNull(MusicPlayer.this.getActivity())).getPlaybackState().getState();
        if (state != PlaybackStateCompat.STATE_PAUSED && state != PlaybackStateCompat.STATE_STOPPED) {
            btnPlayPause.setImageResource(R.drawable.ic_pause);
        }
        if (state != PlaybackStateCompat.STATE_PLAYING) {
            btnPlayPause.setImageResource(R.drawable.ic_play);
        }

        this.btnPlayPause.setOnClickListener(v -> {
            int pdState = MediaControllerCompat.getMediaController(Objects.requireNonNull(MusicPlayer.this.getActivity())).getPlaybackState().getState();
            if (pdState != PlaybackStateCompat.STATE_PAUSED)
                MediaControllerCompat.getMediaController(MusicPlayer.this.getActivity()).getTransportControls().pause();
            if (pdState != PlaybackStateCompat.STATE_PLAYING)
                MediaControllerCompat.getMediaController(MusicPlayer.this.getActivity()).getTransportControls().play();
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

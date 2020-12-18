package com.myappdeport.service.usecase.media;

import android.app.Notification;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.media.MediaBrowserServiceCompat;

import com.myappdeport.model.entity.functional.Song;
import com.myappdeport.utils.MediaUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.Q)
public class MediaPlayBackService extends MediaBrowserServiceCompat {
    private static final String ID_ROOT = "ID_ROOT";
    public final String TAG = MediaPlayBackService.class.getSimpleName();

    public MediaSessionCompat mediaSessionCompat;
    public PlaybackStateCompat.Builder playbackStateBuilder;
    public MediaNotificationManager mediaNotificationManager;
    public MediaPlayer mediaPlayer;
    public MediaSessionCallback mediaSessionCallback;
    //public MusicViewModel musicViewModel;

    private List<Song> songs;
    private int currentSong = 0;
    private final MediaDataBinder mediaDataBinder = new MediaDataBinder();

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onCreate() {
        Log.e(TAG, "CREATE PLAYBACK SERVICE");
        super.onCreate();
        mediaSessionCompat = new MediaSessionCompat(this, "MusicService");
        mediaSessionCompat.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS |
                        MediaSessionCompat.FLAG_HANDLES_QUEUE_COMMANDS);
        playbackStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY |
                        PlaybackStateCompat.ACTION_PLAY_PAUSE |
                        PlaybackStateCompat.ACTION_SKIP_TO_NEXT |
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                        PlaybackStateCompat.ACTION_PREPARE |
                        PlaybackStateCompat.ACTION_SEEK_TO)
                .setState(PlaybackStateCompat.STATE_CONNECTING, 0, 1f);
        mediaSessionCompat.setPlaybackState(playbackStateBuilder.build());
        //Insert session controller;
        mediaSessionCallback = new MediaSessionCallback();
        mediaSessionCompat.setCallback(mediaSessionCallback);
        setSessionToken(mediaSessionCompat.getSessionToken());
        mediaNotificationManager = new MediaNotificationManager(this);
        //musicViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()).create(MusicViewModel.class);
        //songs = musicViewModel.getAllSongs(this.getApplicationContext()).getValue();
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "DESTROY PLAYBACK SERVICE");
        super.onDestroy();
    }

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        return new BrowserRoot(ID_ROOT, rootHints);
    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull final Result<List<MediaBrowserCompat.MediaItem>> result) {
        //result.detach();
        //result.sendResult(musicViewModel.getMediaItems(this));
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return super.onBind(intent);
        } else {
            return this.mediaDataBinder;
        }
    }

    public MediaMetadataCompat getCurrentMetadata() {
        if (songs == null || songs.isEmpty()) {
            return null;
        } else {
            return MediaUtils.getMediaMetadataFromSong(songs.get(currentSong), MediaPlayBackService.this.getApplicationContext());
        }
    }

    // ==================== FOR MAIN ACTIVITY CONTROLLER DATA ================================
    public class MediaDataBinder extends Binder {
        public MediaPlayBackService getMediaPlaybackService() {
            return MediaPlayBackService.this;
        }
    }

    public void resetMediaItem() {
        this.songs = new ArrayList<>();
        this.currentSong = 0;
    }

    public int getCurrentPosition() {
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentPosition();
        }
        return -1;
    }

    public void setMediaQueue(List<Song> songs) {
        this.songs = songs;
    }
    // ========================= END FOR ACTIVITY CONTROLLER DATA =============================

    public MediaNotificationManager getMediaNotificationManager() {
        return this.mediaNotificationManager;
    }

    public class MediaSessionCallback extends MediaSessionCompat.Callback {
        private final String TAG = MediaSessionCallback.class.getSimpleName();

        public MediaSessionCallback() {

        }

        public void setSongs(List<Song> songs) {
            MediaPlayBackService.this.songs = songs;
        }

        @RequiresApi(api = Build.VERSION_CODES.Q)
        public void setCurrentSong(int currentSong) {
            if (currentSong > -1 && currentSong < songs.size()) {
                MediaPlayBackService.this.currentSong = currentSong;
                this.onPrepare();
            } else {
                Log.wtf(TAG, "La posición de la pista actual no puede ser negativa ni ser mayor de la pista actuasl.");
            }
        }

        private PlaybackStateCompat.Builder getBuilderByPlaybackAction(int actionState, long position) {
            PlaybackStateCompat.Builder builder = new PlaybackStateCompat.Builder()
                    .setActions(getAvailableActions(actionState))
                    .setState(actionState, position, 1f);
            /*
             * ActionEvent On Notification
             */
            onPlaybackChange(builder.build());
            return builder;
        }

        @PlaybackStateCompat.Actions
        private long getAvailableActions(int actualState) {
            long actions = PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID
                    | PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH
                    | PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                    | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS;
            switch (actualState) {
                case PlaybackStateCompat.STATE_STOPPED:
                    actions |= PlaybackStateCompat.ACTION_PLAY
                            | PlaybackStateCompat.ACTION_PAUSE;
                    break;
                case PlaybackStateCompat.STATE_PLAYING:
                    actions |= PlaybackStateCompat.ACTION_STOP
                            | PlaybackStateCompat.ACTION_PAUSE
                            | PlaybackStateCompat.ACTION_SEEK_TO;
                    break;
                case PlaybackStateCompat.STATE_PAUSED:
                    actions |= PlaybackStateCompat.ACTION_PLAY
                            | PlaybackStateCompat.ACTION_STOP;
                    break;
                default:
                    actions |= PlaybackStateCompat.ACTION_PLAY
                            | PlaybackStateCompat.ACTION_PLAY_PAUSE
                            | PlaybackStateCompat.ACTION_STOP
                            | PlaybackStateCompat.ACTION_PAUSE;
            }
            return actions;
        }

        @Override
        public boolean onMediaButtonEvent(Intent mediaButtonEvent) {
            return super.onMediaButtonEvent(mediaButtonEvent);
        }

        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public void onPrepare() {
            if (!songs.isEmpty()) {
                Log.e(TAG, "PREPARING MEDIA");
                mediaSessionCompat.setMetadata(MediaUtils.getMediaMetadataFromSong(songs.get(currentSong), MediaPlayBackService.this.getApplicationContext()));
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource(MediaPlayBackService.this.getApplication(), songs.get(currentSong).getSongPath());
                        mediaPlayer.prepare();
                        Log.e(TAG, "SONG DURATION: " + mediaPlayer.getDuration());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                mediaSessionCompat.setPlaybackState(getBuilderByPlaybackAction(PlaybackStateCompat.STATE_CONNECTING, 0).build());
                if (!mediaSessionCompat.isActive()) {
                    mediaSessionCompat.setActive(true);
                }
            } else {
                Log.e(TAG, "DON'T EXIST MEDIA");
            }
            super.onPrepare();
        }


        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public void onPlay() {
            Log.e(TAG, "PLAY");
            onPrepare();
            if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                final int position = mediaPlayer != null ? mediaPlayer.getCurrentPosition() : 0;
                onSeekTo(position);
            }
            super.onPlay();
        }

        @Override
        public void onPause() {
            Log.e(TAG, "PAUSE");
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                int position = mediaPlayer != null ? mediaPlayer.getCurrentPosition() : 0;
                mediaSessionCompat.setPlaybackState(getBuilderByPlaybackAction(PlaybackStateCompat.STATE_PAUSED, position).build());
                mediaPlayer.pause();
            }
            super.onPause();
        }

        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public void onSkipToNext() {
            Log.e(TAG, "NEXT");
            MediaPlayBackService.this.currentSong = (MediaPlayBackService.this.currentSong + 1) % songs.size();
            onPrepare();
            onStop();
            onPlay();
            super.onSkipToNext();
        }

        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public void onSkipToPrevious() {
            Log.e(TAG, "PREVIOUS");
            MediaPlayBackService.this.currentSong = (MediaPlayBackService.this.currentSong - 1) % songs.size();
            onPrepare();
            onStop();
            onPlay();
            super.onSkipToPrevious();
        }

        @Override
        public void onStop() {
            Log.e(TAG, "STOP");
            super.onStop();
            if (mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }

        @Override
        public void onSeekTo(final long pos) {
            Log.e(TAG, "SEEK_TO: " + pos);
            mediaSessionCompat.setPlaybackState(getBuilderByPlaybackAction(PlaybackStateCompat.STATE_PLAYING, pos).build());
            mediaPlayer.seekTo((int) pos);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @RequiresApi(api = Build.VERSION_CODES.Q)
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Log.e(TAG, "COMPLETE MEDIA");
                    mediaSessionCompat.setPlaybackState(getBuilderByPlaybackAction(PlaybackStateCompat.STATE_BUFFERING, 0).build());
                    onSkipToNext();
                }
            });
            super.onSeekTo(pos);
        }

        @Override
        public void onPlayFromMediaId(String mediaId, Bundle extras) {
            Log.e(TAG, "ON_PLAY_MEDIA_ID");
            currentSong = Integer.parseInt(mediaId);
            onStop();
            onPlay();
            super.onPlayFromMediaId(mediaId, extras);
        }


    }

    private void onPlaybackChange(PlaybackStateCompat actionState) {
        switch (actionState.getState()) {
            case PlaybackStateCompat.STATE_PLAYING:
                moveServiceToStartedState(actionState);
                break;
            case PlaybackStateCompat.STATE_PAUSED:
                updateNotificationForPause(actionState);
                break;
            case PlaybackStateCompat.STATE_STOPPED:
                moveServiceOutOfStartedState(actionState);
                break;
        }
    }

    private boolean mServiceInStartedState;

    private void moveServiceToStartedState(PlaybackStateCompat state) {
        Log.e("TAGF", "moveServiceToStartedState");
        Notification notification =
                mediaNotificationManager.getNotification(
                        MediaUtils.getMediaMetadataFromSong(songs.get(currentSong), MediaPlayBackService.this), state, getSessionToken());

        if (!mServiceInStartedState) {
            ContextCompat.startForegroundService(
                    MediaPlayBackService.this,
                    new Intent(MediaPlayBackService.this, MediaPlayBackService.class));
            mServiceInStartedState = true;
        }

        startForeground(MediaNotificationManager.NOTIFICATION_ID, notification);
    }

    private void updateNotificationForPause(PlaybackStateCompat state) {
        Log.e("TAGF", "updateNotificationForPause");
        stopForeground(false);
        Notification notification =
                mediaNotificationManager.getNotification(
                        MediaUtils.getMediaMetadataFromSong(songs.get(currentSong), MediaPlayBackService.this), state, getSessionToken());
        mediaNotificationManager.getNotificationManager()
                .notify(MediaNotificationManager.NOTIFICATION_ID, notification);
    }

    private void moveServiceOutOfStartedState(PlaybackStateCompat state) {
        Log.e("TAGF", "moveServiceOutOfStartedState");
        stopForeground(true);
        stopSelf();
        mServiceInStartedState = false;
    }

}
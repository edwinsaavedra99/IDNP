package com.myappdeport.service.usecase.media;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.media.session.MediaButtonReceiver;
import com.myappdeport.R;
import com.myappdeport.view.fragments.MusicPlayer;

/**
 * Creación de nuevas notificaciones.
 */
public class MediaNotificationManager {
    public static final int NOTIFICATION_ID = 413;
    private static final String CHANNEL_ID = "MUSIC_PLAYER";
    private static final int REQUEST_CODE = 501;
    private static final String TAG = MediaNotificationManager.class.getSimpleName();
    private final MediaPlayBackService mediaPlayBackService;
    private final NotificationManager notificationManager;
    private final NotificationCompat.Action playAction;
    private final NotificationCompat.Action pauseAction;
    private final NotificationCompat.Action nextAction;
    private final NotificationCompat.Action previousAction;

    private final int ICON_PLAY = R.drawable.ic_play_arrow_white_24dp;
    private final int ICON_PAUSE = R.drawable.ic_pause_white_24dp;
    private final int ICON_NEXT = R.drawable.ic_skip_next_white_24dp;
    private final int ICON_PREV = R.drawable.ic_skip_previous_white_24dp;
    private final int ICON_PLAY_NOT = R.mipmap.ic_launcher;


    public MediaNotificationManager(MediaPlayBackService service) {
        this.mediaPlayBackService = service;
        notificationManager = (NotificationManager) mediaPlayBackService.getSystemService(Context.NOTIFICATION_SERVICE);
        this.playAction = new NotificationCompat.Action(
                ICON_PLAY,
                this.mediaPlayBackService.getString(R.string.label_play),
                MediaButtonReceiver.
                        buildMediaButtonPendingIntent(mediaPlayBackService, PlaybackStateCompat.ACTION_PLAY));
        this.pauseAction = new NotificationCompat.Action(
                ICON_PAUSE,
                this.mediaPlayBackService.getString(R.string.label_pause),
                MediaButtonReceiver.
                        buildMediaButtonPendingIntent(mediaPlayBackService, PlaybackStateCompat.ACTION_PAUSE));
        this.previousAction = new NotificationCompat.Action(
                ICON_PREV,
                this.mediaPlayBackService.getString(R.string.label_previous),
                MediaButtonReceiver.
                        buildMediaButtonPendingIntent(mediaPlayBackService, PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS));
        this.nextAction = new NotificationCompat.Action(
                ICON_NEXT,
                this.mediaPlayBackService.getString(R.string.label_next),
                MediaButtonReceiver.
                        buildMediaButtonPendingIntent(mediaPlayBackService, PlaybackStateCompat.ACTION_SKIP_TO_NEXT));
        notificationManager.cancelAll();
    }

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    public Notification getNotification(MediaMetadataCompat mediaMetadataCompat, PlaybackStateCompat state, MediaSessionCompat.Token token) {
        boolean isPlaying = state.getState() == PlaybackStateCompat.STATE_PLAYING;
        MediaDescriptionCompat description = mediaMetadataCompat.getDescription();
        NotificationCompat.Builder builder =
                buildNotification(state, token, isPlaying, description);
        return builder.build();
    }

    private NotificationCompat.Builder buildNotification(PlaybackStateCompat state, MediaSessionCompat.Token token, boolean isPlaying, MediaDescriptionCompat description) {
        this.createChannelNotification();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mediaPlayBackService, CHANNEL_ID)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setMediaSession(token)
                        .setShowActionsInCompactView(0, 1, 2)
                        .setShowCancelButton(true)
                        .setCancelButtonIntent(MediaButtonReceiver
                                .buildMediaButtonPendingIntent(mediaPlayBackService,
                                        PlaybackStateCompat.ACTION_STOP)))
                .setColor(ContextCompat.getColor(mediaPlayBackService, R.color.notification_color))
                .setSmallIcon(ICON_PLAY_NOT)
                .setContentIntent(createContentIntent())
                .setContentTitle(description.getTitle())
                .setContentText(description.getSubtitle())
                .setLargeIcon(description.getIconBitmap())
                .setDeleteIntent(MediaButtonReceiver
                        .buildMediaButtonPendingIntent(mediaPlayBackService, PlaybackStateCompat.ACTION_STOP))
                .setSound(null)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        if ((state.getActions() & PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS) != 0) {
            builder.addAction(previousAction);
        }
        builder.addAction(isPlaying ? pauseAction : playAction);
        if ((state.getActions() & PlaybackStateCompat.ACTION_SKIP_TO_NEXT) != 0) {
            builder.addAction(nextAction);
        }
        return builder;
    }

    private PendingIntent createContentIntent() {
        Intent openUI = new Intent(mediaPlayBackService, MusicPlayer.class);
        openUI.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(
                mediaPlayBackService, REQUEST_CODE, openUI, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    private void createChannelNotification() {
        if (notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "MEDIA_SESSION_DARK_CAT", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("MEDIA SESSION AND MEDIA LAYER");
            notificationChannel.enableLights(false);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(false);
            notificationChannel.setSound(null, null);
            //notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(notificationChannel);
            Log.e(TAG, "Creating new channel for notification.");
        } else {
            Log.e(TAG, "Exiting channel notification reused.");
        }
    }
}

package com.myappdeport.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.util.Log;
import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;
import com.myappdeport.R;
import com.myappdeport.model.entity.functional.Song;

import java.util.ArrayList;
import java.util.List;

public class MediaUtils {

    private static final String TAG = MediaUtils.class.getSimpleName();
    /**
     * Default Icon for application notification or albumArt
     */
    public static int DEFAULT_ALBUM_ART = R.mipmap.ic_launcher;

    /**
     * Transforma las musicas en metadatos pero sin cargar su albumArt.
     *
     * @param songs Son las canciones.
     * @return Son los datos de la musica en MediaMetadata.
     */
    public static List<MediaMetadataCompat> songToMetadata(List<Song> songs) {
        List<MediaMetadataCompat> mediaMetadataCompats = new ArrayList<>();
        for (Song song : songs) {
            MediaMetadataCompat.Builder builder = new MediaMetadataCompat.Builder()
                    .putString(MediaMetadataCompat.METADATA_KEY_TITLE, song.getTitle())
                    .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, song.getDuration())
                    .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, song.getAlbum())
                    .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, song.getArtist())
                    .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, song.getSongPath().getPath());
            mediaMetadataCompats.add(builder.build());
        }
        return mediaMetadataCompats;
    }

    /**
     * Transforma la lista de Musica en una media description.
     *
     * @param songs   Son todas las canciones en el dispositivo.
     * @param context Es el contexto de donde se obtendra el Icon Uri.
     * @return Es la metada description.
     */
    public static List<MediaDescriptionCompat> songToMediaDescription(List<Song> songs, Context context) {
        List<MediaDescriptionCompat> mediaMetadataCompats = new ArrayList<>();
        for (Song song : songs) {
            MediaDescriptionCompat.Builder mediaMetadataBuilder = new MediaDescriptionCompat.Builder()
                    .setTitle(song.getTitle())
                    .setSubtitle(song.getArtist() + "-" + song.getAlbum())
                    .setDescription(song.getDuration() + "")
                    .setMediaUri(song.getSongPath())
                    //.setIconUri(getUriToResource(context, DEFAULT_ALBUM_ART))
                    .setMediaId("" + songs.size());
            mediaMetadataCompats.add(mediaMetadataBuilder.build());
        }
        return mediaMetadataCompats;
    }

    /**
     * Obtiene la URI de un recurso android.
     *
     * @param context Es el contexto de donde se obtendra la URI.
     * @param resId   Es el id del recurso android.
     * @return Es la Uri solicitada respecto al id del recurso.
     * @throws Resources.NotFoundException Es lanzado cuando el recurso no existe.
     */
    public static Uri getUriToResource(@NonNull Context context, @AnyRes int resId) throws Resources.NotFoundException {
        Resources res = context.getResources();
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + res.getResourcePackageName(resId)
                + '/' + res.getResourceTypeName(resId)
                + '/' + res.getResourceEntryName(resId));
    }

    /**
     * Obtiene los metadatos de la uns canción junto a su icono asociado si es que lo tiene y sino retorna un icono de la app por defecto.
     *
     * @param song    Es la musica de la cual se quiere traer sus metadatos.
     * @param context Es el contexto del cual se obtendra el album art.
     * @return Es el metadato con todos los attributos de la canción inluyendo el album art.
     */
    public static MediaMetadataCompat getMediaMetadataFromSong(Song song, Context context) {
        Bitmap albumArt = getBitmapFromUri(song.getSongPath(), context);
        if (albumArt == null) {
            Log.e(TAG, "Setting default albumArt.");
            albumArt = BitmapFactory.decodeResource(context.getResources(), DEFAULT_ALBUM_ART);
        }
        return new MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, song.getTitle())
                .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, song.getDuration())
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, song.getAlbum())
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, song.getArtist())
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, song.getSongPath().getPath())
                .putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, albumArt)
                .build();
    }

    /**
     * Obtiene un bitmap de la musica.
     *
     * @param uri     Es la dirección de la musica.
     * @param context Es el contexto del cual se obtendra la musica.
     * @return Es el bitmap del album art.
     */
    public static Bitmap getBitmapFromUri(Uri uri, Context context) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(context, uri);
        byte[] embeddedPicture = mediaMetadataRetriever.getEmbeddedPicture();
        Bitmap albumArt = null;
        if (embeddedPicture != null) {
            albumArt = BitmapFactory.decodeByteArray(embeddedPicture, 0, embeddedPicture.length);
        } else {
            Log.e(TAG, "No Album Art");
        }
        mediaMetadataRetriever.release();
        return albumArt;
    }
}
